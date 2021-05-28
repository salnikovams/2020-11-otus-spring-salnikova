package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Limit;
import ru.otus.spring.dto.*;
import ru.otus.spring.exception.InputParameterException;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.repositories.LimitRepository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
class LimitServiceTest {

    @Autowired
    private LimitService limitService;

    @Autowired
    private LimitRepository limitRepository;


    @Test
    @Transactional(readOnly = true)
    void testCreateLimit() throws Exception {

        LimitRequestDTO limitDTO = buildCreateLimitDTO();

        LimitDTO createdLimit  = limitService.createLimit(limitDTO);

        Long limitId = createdLimit.getId();
        Assertions.assertNotNull(limitId);

        Optional<Limit> expectedLimit = limitRepository.findById(limitId);
        assertThat(expectedLimit.isPresent()).isNotNull();
        assertThat(expectedLimit.get().toDTO()).isEqualTo(createdLimit);
    }


    @Test
    @Transactional(readOnly = true)
    void testCreateLimitWithWrongCurrency() throws Exception {

        LimitRequestDTO limitDTO = buildLimitDTOWithWrongCurrency();
        assertThrows(InputParameterException.class,
                () -> limitService.createLimit(limitDTO));
    }

    @Test
    @Transactional(readOnly = true)
    void testCreateLimitWithSameName() throws Exception {

        LimitRequestDTO limitDTO = buildCreateLimitDTO();

        LimitDTO createdLimit  = limitService.createLimit(limitDTO);

        Long limitId = createdLimit.getId();
        Assertions.assertNotNull(limitId);

        assertThrows(InputParameterException.class,
                () -> limitService.createLimit(limitDTO));
    }

    @Test
    @Transactional(readOnly = true)
    void testUpdate() throws Exception {
        LimitRequestDTO limitDTO = buildCreateLimitDTO();

        LimitDTO createdLimit  = limitService.createLimit(limitDTO);

        Long limitId = createdLimit.getId();
        Assertions.assertNotNull(limitId);

        LimitRequestDTO limitUpdateDTO = buildCreateLimitDTO();
        limitUpdateDTO.setLimitConditionList(Collections.emptyList());
        limitUpdateDTO.setName("New Name");
        LimitDTO updatedLimit = limitService.updateLimit(limitId, limitUpdateDTO);

        Assertions.assertEquals(updatedLimit.getName(), limitUpdateDTO.getName());
        Assertions.assertEquals(updatedLimit.getLimitConditionList().size(), 0);
    }

    @Test
    @Transactional(readOnly = true)
    void testUpdateWithSameName() throws Exception {
        LimitRequestDTO limitDTO = buildCreateLimitDTO();

        LimitDTO createdLimit  = limitService.createLimit(limitDTO);

        Long limitId = createdLimit.getId();
        Assertions.assertNotNull(limitId);

        LimitRequestDTO limitUpdateDTO = buildCreateLimitDTO();

        LimitDTO updateDTO = limitService.updateLimit(limitId, limitUpdateDTO);

        assertThat(limitUpdateDTO.getName())
                .isEqualTo(updateDTO.getName());
        assertThat(limitUpdateDTO.getBreakConditionValue())
                .isEqualTo(updateDTO.getBreakConditionValue());
        assertThat(limitUpdateDTO.getCurrencyISOCode())
                .isEqualTo(updateDTO.getCurrency().getIsoCode());
        assertThat(limitUpdateDTO.getStartDate())
                .isEqualTo(updateDTO.getStartDate());
        assertThat(limitUpdateDTO.getEndDate())
                .isEqualTo(updateDTO.getEndDate());
        assertThat(limitUpdateDTO.getLimitConditionList())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("id", "limitId")
                .isEqualTo(updateDTO.getLimitConditionList());

    }


    @Test
    @Transactional(readOnly = true)
    void testUpdateOtherWithExistingName() throws Exception {
        LimitRequestDTO limitDTO1 = buildCreateLimitDTO();

        limitService.createLimit(limitDTO1);

        LimitRequestDTO limitDTO2 = buildCreateLimitDTO();

        limitDTO2.setName("Other Name");

        LimitDTO createdLimit2  = limitService.createLimit(limitDTO2);

        Long limitId2 = createdLimit2.getId();


        LimitRequestDTO limitUpdateDTO = buildCreateLimitDTO();

        assertThrows(InputParameterException.class,
                () -> limitService.updateLimit(limitId2, limitUpdateDTO));

    }

    @Test
    @Transactional(readOnly = true)
    void testDeleteLimit() throws Exception {
        LimitRequestDTO limitDTO1 = buildCreateLimitDTO();

        LimitDTO limitDTO = limitService.createLimit(limitDTO1);

        Long limitID = limitDTO.getId();
        limitService.deleteLimitById(limitID);

        assertThrows(NotFoundException.class,
                () -> limitService.getLimitById(limitID));

    }

    @Test
    @Transactional(readOnly = true)
    void testDeleteLimitWithWrongId() throws Exception {
        LimitRequestDTO limitDTO1 = buildCreateLimitDTO();

        LimitDTO limitDTO = limitService.createLimit(limitDTO1);

        Long limitID = limitDTO.getId();

        assertThrows(InputParameterException.class,
                () -> limitService.deleteLimitById(limitID+1));

    }

    @Test
    @Transactional(readOnly = true)
    void testcheckLimitByPAramWithExceed() throws Exception {
        LimitRequestDTO limitDTO1 = buildCheckLimitDTO();

        limitService.createLimit(limitDTO1);

        List<ObjectConditionDTO> conditionList = new ArrayList<>();

        conditionList.add(new ObjectConditionDTO(1, 1L));
        conditionList.add(new ObjectConditionDTO(2, 2L));

        ObjectToCheckRequestDTO objectToCheckRequestDTO = new ObjectToCheckRequestDTO(new BigDecimal(60.0),  "643", conditionList);

        NotificationDTO notifDTO = limitService.checkLimitByParam(objectToCheckRequestDTO);
        assertEquals(notifDTO.getNtfCode().longValue(), 1L);

    }

    @Test
    @Transactional(readOnly = true)
    void testCheckLimitByParamWithoutExceed() throws Exception {
        LimitRequestDTO limitDTO1 = buildCheckLimitDTO();

        limitService.createLimit(limitDTO1);

        List<ObjectConditionDTO> conditionList = new ArrayList<>();

        conditionList.add(new ObjectConditionDTO(1, 1L));
        conditionList.add(new ObjectConditionDTO(2, 2L));

        ObjectToCheckRequestDTO objectToCheckRequestDTO = new ObjectToCheckRequestDTO(new BigDecimal(30.0),  "643", conditionList);

        NotificationDTO notifDTO = limitService.checkLimitByParam(objectToCheckRequestDTO);
        assertEquals(notifDTO.getNtfCode().longValue(), 0L);

    }

    @Test
    @Transactional(readOnly = true)
    void testCheckLimitByParamWithoutLimitExceed() throws Exception {
        LimitRequestDTO limitDTO1 = buildCheckLimitDTO();

        limitService.createLimit(limitDTO1);

        List<ObjectConditionDTO> conditionList = new ArrayList<>();

        conditionList.add(new ObjectConditionDTO(1, 1L));
        conditionList.add(new ObjectConditionDTO(2, 3L));

        ObjectToCheckRequestDTO objectToCheckRequestDTO = new ObjectToCheckRequestDTO(new BigDecimal(30.0),  "643", conditionList);

        NotificationDTO notifDTO = limitService.checkLimitByParam(objectToCheckRequestDTO);
        assertEquals(notifDTO.getNtfCode().longValue(), 0L);

    }

    private LimitRequestDTO buildCheckLimitDTO() throws  java.text.ParseException {
        LimitRequestDTO limitDTO = new LimitRequestDTO();
        limitDTO.setName("LimitNumberOne");
        limitDTO.setComment("LimitNumberOne comment");
        limitDTO.setBreakConditionValue(new BigDecimal(40.0));
        limitDTO.setStartDate(new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2021"));
        limitDTO.setEndDate(new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2025"));

        List<LimitConditionCreateDTO> limitConditionDTOList = new ArrayList<>();

        LimitConditionCreateDTO conditionDTO1 = new LimitConditionCreateDTO();
        conditionDTO1.setConditionKind(1);
        conditionDTO1.setConditionValue(1L);
        limitConditionDTOList.add(conditionDTO1);

        LimitConditionCreateDTO conditionDTO2 = new LimitConditionCreateDTO();
        conditionDTO2.setConditionKind(2);
        conditionDTO2.setConditionValue(2L);
        limitConditionDTOList.add(conditionDTO2);

        limitDTO.setLimitConditionList(limitConditionDTOList);

        limitDTO.setCurrencyISOCode("643");

        return limitDTO;
    }


    private LimitRequestDTO buildCreateLimitDTO() {
        LimitRequestDTO limitDTO = new LimitRequestDTO();
        limitDTO.setName("LimitNumberOne");
        limitDTO.setComment("LimitNumberOne comment");
        limitDTO.setBreakConditionValue(new BigDecimal(40.0));

        List<LimitConditionCreateDTO> limitConditionDTOList = new ArrayList<>();

        LimitConditionCreateDTO conditionDTO1 = new LimitConditionCreateDTO();
        conditionDTO1.setConditionKind(1);
        conditionDTO1.setConditionValue(1L);
        limitConditionDTOList.add(conditionDTO1);

        LimitConditionCreateDTO conditionDTO2 = new LimitConditionCreateDTO();
        conditionDTO2.setConditionKind(1);
        conditionDTO2.setConditionValue(1L);
        limitConditionDTOList.add(conditionDTO2);

        limitDTO.setLimitConditionList(limitConditionDTOList);

        limitDTO.setCurrencyISOCode("643");

        return limitDTO;
    }


    private LimitRequestDTO buildLimitDTOWithWrongCurrency() {
        LimitRequestDTO limitDTO = new LimitRequestDTO();
        limitDTO.setName("LimitNumberOne");
        limitDTO.setComment("LimitNumberOne comment");
        limitDTO.setBreakConditionValue(new BigDecimal(40.0));

        List<LimitConditionCreateDTO> limitConditionDTOList = new ArrayList<>();

        LimitConditionCreateDTO conditionDTO1 = new LimitConditionCreateDTO();
        conditionDTO1.setConditionKind(1);
        conditionDTO1.setConditionValue(1L);
        limitConditionDTOList.add(conditionDTO1);

        LimitConditionCreateDTO conditionDTO2 = new LimitConditionCreateDTO();
        conditionDTO2.setConditionKind(1);
        conditionDTO2.setConditionValue(1L);
        limitConditionDTOList.add(conditionDTO2);

        limitDTO.setLimitConditionList(limitConditionDTOList);

        limitDTO.setCurrencyISOCode("522");

        return limitDTO;
    }

}