package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Currency;
import ru.otus.spring.domain.Limit;
import ru.otus.spring.domain.LimitCondition;
import ru.otus.spring.dto.*;
import ru.otus.spring.enums.NotificationCode;
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

    private final MessageSource messageSource;

    private final Locale locale;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LimitServiceImpl(LimitRepository limitRepository, CurrencyRepository currencyRepository,
                            LimitConditionRepository limitConditionRepository, EntityManager entityManager,
                            MessageSource messageSource,
                            @Value( "${application.locale}")Locale locale) {
        this.limitRepository = limitRepository;
        this.currencyRepository = currencyRepository;
        this.limitConditionRepository = limitConditionRepository;
        this.entityManager = entityManager;
        this.messageSource = messageSource;
        this.locale = locale;
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
            String limitName = limit.getName();
            if (checkRequest.getAmount().compareTo(breakValue)>0){
               // return new NotificationDTO(NotificationCode.LIMIT_CHECK_EXCESS.getCode(), String.format("Limit %s is Exceeded", limit.getName()));
                //messageSource.getMessage("number.of.correct.answers",  new Integer[]{studentCorrectAnswerQty}, locale)
                return new NotificationDTO(NotificationCode.LIMIT_CHECK_EXCESS.getCode(),
                        messageSource.getMessage(NotificationCode.LIMIT_CHECK_EXCESS.getMessage(), new String[]{limitName}, locale));
            }
        }

        return new NotificationDTO(NotificationCode.LIMIT_CHECK_SUCCESS.getCode(), "OK");

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
            throw new InputParameterException(messageSource.getMessage(NotificationCode.LIMIT_NOT_FOUND.getMessage(), new Long[]{limitId}, locale)/*String.format("Limit is not found by id %s", limitId)*/);
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
            throw new InputParameterException(messageSource.getMessage(NotificationCode.LIMIT_NAME_IS_MANDATORY.getMessage(), null, locale/*"Name is a mandatory parameter"*/));
        }
        if (limitDTO.getBreakConditionValue() == null){
            throw new InputParameterException(messageSource.getMessage(NotificationCode.LIMIT_BREAKCONDITIONVALUE_IS_MANDATORY.getMessage(), null, locale)/*"BreakConditionValue is a mandatory parameter"*/);
        }
        if (limitDTO.getLimitConditionList() == null){
            throw new InputParameterException(messageSource.getMessage(NotificationCode.LIMIT_CONDITIONLIST_IS_MANDATORY.getMessage(), null, locale))/*"ConditionList is a mandatory parameter"*/;
        }
        if (limitDTO.getCurrencyId() == null && limitDTO.getCurrencyISOCode() == null){
            throw new InputParameterException(messageSource.getMessage(NotificationCode.LIMIT_CONDITIONLIST_IS_MANDATORY.getMessage(), null, locale)/*"You need to set currency ID or ISOCode"*/);
        }
    }



    private void checkLimitExistsByName(String name) throws InputParameterException {
        if (limitRepository.existsByName(name)) {
            throw new InputParameterException(
                    messageSource.getMessage(NotificationCode.LIMIT_WITH_NAME_ALREADYEXISTS.getMessage(), new String[]{name}, locale)
                    /*String.format("Limit with name %s already exists", name)*/);
        }
    }

    private void checkOtherLimitExistsByName(Long limitId, String name) throws InputParameterException {
        List<Limit> existingLimits =limitRepository.findByIdNotAndName(limitId, name);
        if (!existingLimits.isEmpty()) {
            throw new InputParameterException(
                   /* String.format("Limit with name %s already exists", name)*/
                    messageSource.getMessage(NotificationCode.LIMIT_WITH_NAME_ALREADYEXISTS.getMessage(), new String[]{name}, locale));
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
        } else throw new InputParameterException(
                messageSource.getMessage(NotificationCode.LIMIT_CURRENCY_NOTFOUND_BYPARAM.getMessage(),
                        new String[]{(currencyID != null)?currencyID.toString():"null", isoCode}, locale)
                /*String.format("Currency not found by id=%s or code=%s", currencyID, isoCode)*/);
    }
}
