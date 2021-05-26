package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "LimitCondition", description = "Условие отбора лимитов")
public class LimitConditionCreateDTO {

    @ApiModelProperty(notes = "Вид условия контроля лимитов", example = "1")
    private Integer conditionKind;

    @ApiModelProperty(notes = "Значение условия контроля лимитов", example = "1")
    private Long conditionValue;

}
