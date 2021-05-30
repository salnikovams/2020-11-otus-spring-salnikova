package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ObjectToCheck", description = "Объект на контроль лимитов")
public class ObjectToCheckRequestDTO {

    @ApiModelProperty(notes = "Сумма объекта", example = "1")
    private BigDecimal amount;

    @ApiModelProperty(notes = "Код валюты", example = "643")
    private String currencyISOCode;

    @ApiModelProperty(notes = "Условия отбора")
    private List<ObjectConditionDTO> conditionList;

}
