package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Currency;
import ru.otus.spring.domain.Limit;
import ru.otus.spring.domain.LimitCondition;
import ru.otus.spring.dto.*;
import ru.otus.spring.exception.InputParameterException;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repositories.CurrencyRepository;
import ru.otus.spring.repositories.LimitRepository;
import ru.otus.spring.repositories.LimitConditionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LimitServiceImpl implements LimitService {

    private final LimitRepository limitRepository;

    private final CurrencyRepository currencyRepository;

    private final LimitConditionRepository limitConditionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LimitServiceImpl(LimitRepository limitRepository, CurrencyRepository currencyRepository,
                            LimitConditionRepository limitConditionRepository, EntityManager entityManager) {
        this.limitRepository = limitRepository;
        this.currencyRepository = currencyRepository;
        this.limitConditionRepository = limitConditionRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public LimitDTO createLimit(LimitRequestDTO limitDTO) throws InputParameterException {
        Limit limit = getLimitEntityFromCreateDTO(limitDTO);
        limitRepository.save(limit);
        return limit.toDTO();
    }

    @Override
    @Transactional
    public LimitDTO updateLimit(Long id, LimitRequestDTO limitDTO)  throws InputParameterException{
        Limit limit = findByIdAndCheckExists(id);
        limit = updateLimitEntityFromDTO(limit, limitDTO);
        limitRepository.save(limit);
        return limit.toDTO();
    }

    @Override
    @Transactional
    public void deleteLimitById(Long id) throws InputParameterException{
        findByIdAndCheckExists(id);
        limitRepository.deleteById(id);
    }

    @Override
    public LimitDTO getLimitById(Long id) {
        Optional<Limit> optionalLimit = limitRepository.findById(id) ;
        return optionalLimit.orElseThrow(NotFoundException::new).toDTO();
    }


    @Override
    public List<LimitDTO> getAllLimits() {
        return limitRepository.findAll().stream().map(Limit::toDTO).collect(Collectors.toList());
    }


    @Override
    public NotificationDTO checkLimitByParam(ObjectToCheckRequestDTO checkRequest) throws InputParameterException {
        List<ObjectConditionDTO> objectConditionList = checkRequest.getConditionList();
        Map<Integer, Long> objConditionIndex = new HashMap<Integer, Long>();
        for (ObjectConditionDTO condition: objectConditionList){
            objConditionIndex.put(condition.getConditionKind(), condition.getConditionValue());
        }
        Date operationDate = new Date();
        List<Limit> limitsByOperDate= limitRepository.findByStartDateBeforeAndEndDate(operationDate);
        List<Limit> selectedLimits = new ArrayList<>();
        for (Limit limit: limitsByOperDate){
            List<LimitCondition> conditionList = limit.getLimitConditionList();

            Iterator<LimitCondition> iterator =  conditionList.iterator();
            while (iterator.hasNext()){
                LimitCondition condition = iterator.next();
                Long currentValue = objConditionIndex.get(condition.getConditionKind());
                if (currentValue != null && currentValue.equals(condition.getConditionValue())){
                    objConditionIndex.remove(condition.getConditionKind());
                    iterator.remove();
                }
            }
            if (conditionList.isEmpty() && objConditionIndex.isEmpty()){
                selectedLimits.add(limit);
            }
        }

        for (Limit limit: selectedLimits){//до первого лимита, который не прошли
            BigDecimal breakValue = limit.getBreakConditionValue();
            if (checkRequest.getAmount().compareTo(breakValue)>0){
                return new NotificationDTO(1L, String.format("Limit %s is Exceeded", limit.getName()));
            }
        }

        return new NotificationDTO(0L, "OK");

    }

    public Limit getLimitEntityFromCreateDTO(LimitRequestDTO limitDTO)  throws InputParameterException {
        checkMandatoryParameters(limitDTO);
        checkLimitExistsByName(limitDTO.getName());
        return getLimitFromDTO(limitDTO);
    }

    public Limit updateLimitEntityFromDTO(Limit limit, LimitRequestDTO limitUpdateDTO)  throws InputParameterException {
        checkMandatoryParameters(limitUpdateDTO);
        checkOtherLimitExistsByName(limit.getId(), limitUpdateDTO.getName());
        Currency currency = getCurrencyByIdOrISOCode(limitUpdateDTO.getCurrencyId(), limitUpdateDTO.getCurrencyISOCode());
        limit.setCurrency(currency);
        limit.setName(limitUpdateDTO.getName());
        limit.setComment(limitUpdateDTO.getComment());
        limit.setStartDate(limitUpdateDTO.getStartDate());
        limit.setEndDate(limitUpdateDTO.getEndDate());
        limit.setBreakConditionValue(limitUpdateDTO.getBreakConditionValue());
        limit.setLimitConditionList(new ArrayList<>());
        for (LimitConditionCreateDTO limitConditionDTO : limitUpdateDTO.getLimitConditionList()) {
            LimitCondition limitCondition = new LimitCondition(limitConditionDTO.getConditionKind(), limitConditionDTO.getConditionValue());
            limitCondition.setLimit(limit);
            limit.getLimitConditionList().add(limitCondition);
        }
        return limit;
    }

    private Limit findByIdAndCheckExists(Long limitId) throws InputParameterException {
        Optional<Limit> limitOptional = limitRepository.findById(limitId);
        if (!limitOptional.isPresent()) {
            throw new InputParameterException(String.format("Limit is not found by id %s", limitId));
        }
        return limitOptional.get();
    }

    private Limit getLimitFromDTO(LimitRequestDTO limitDTO) throws InputParameterException {
        Currency currency = getCurrencyByIdOrISOCode(limitDTO.getCurrencyId(), limitDTO.getCurrencyISOCode());
        Limit limit = new Limit( limitDTO.getName(), limitDTO.getComment(), limitDTO.getStartDate(), limitDTO.getEndDate(), limitDTO.getBreakConditionValue(), currency);
        limit.setLimitConditionList(new ArrayList<>());
        for (LimitConditionCreateDTO limitConditionDTO : limitDTO.getLimitConditionList()) {
            LimitCondition limitCondition = new LimitCondition(limitConditionDTO.getConditionKind(), limitConditionDTO.getConditionValue());
            limitCondition.setLimit(limit);
            limit.getLimitConditionList().add(limitCondition);
        }
        return limit;
    }

    public void checkMandatoryParameters(LimitRequestDTO limitDTO)throws InputParameterException{
        if (limitDTO.getName() == null){
            throw new InputParameterException("Name is a mandatory parameter");
        }
        if (limitDTO.getBreakConditionValue() == null){
            throw new InputParameterException("BreakConditionValue is a mandatory parameter");
        }
        if (limitDTO.getLimitConditionList() == null){
            throw new InputParameterException("ConditionList is a mandatory parameter");
        }
        if (limitDTO.getCurrencyId() == null && limitDTO.getCurrencyISOCode() == null){
            throw new InputParameterException("You need to set currency ID or ISOCode");
        }
    }



    private void checkLimitExistsByName(String name) throws InputParameterException {
        if (limitRepository.existsByName(name)) {
            throw new InputParameterException(
                    String.format("Limit with name %s already exists", name));
        }
    }

    private void checkOtherLimitExistsByName(Long limitId, String name) throws InputParameterException {
        List<Limit> existingLimits =limitRepository.findByIdNotAndName(limitId, name);
        if (!existingLimits.isEmpty()) {
            throw new InputParameterException(
                    String.format("Limit with name %s already exists", name));
        }
    }

    private Currency getCurrencyByIdOrISOCode(Long currencyID, String isoCode) throws InputParameterException {
        Optional<Currency> optionalCurrency = Optional.empty();
        if (currencyID != null) {
            optionalCurrency = currencyRepository.findById(currencyID);
            if (optionalCurrency.isPresent()) {
                return optionalCurrency.get();
            }
        }
        if (isoCode != null) {
            optionalCurrency = currencyRepository.findByIsoCode(isoCode);
        }
        if (optionalCurrency.isPresent()) {
                return optionalCurrency.get();
        } else throw new InputParameterException(String.format("Currency not found by id=%s or code=%s", currencyID, isoCode));
    }

    private Currency getCurrencyByISOCode(String isoCode) throws InputParameterException {
        Optional<Currency> optionalCurrency = currencyRepository.findByIsoCode(isoCode);
        if (optionalCurrency.isPresent()){
            return optionalCurrency.get();
        } else throw new InputParameterException(String.format("Currency not found by name %s", isoCode));
    }


}
