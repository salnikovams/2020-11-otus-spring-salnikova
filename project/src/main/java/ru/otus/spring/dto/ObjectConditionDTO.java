package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ObjectCondition", description = "Параметры объекта контроля")
public class ObjectConditionDTO {

    @ApiModelProperty(notes = "Тип условия отбора", example = "1")
    private Integer conditionKind;

    @ApiModelProperty(notes = "Значение условия отбора", example = "1")
    private Long conditionValue;

}
