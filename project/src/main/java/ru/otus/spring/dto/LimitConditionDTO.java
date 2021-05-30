package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "LimitConditionDTO", description = "Условие отбора лимитов")
public class LimitConditionDTO {
    @ApiModelProperty(notes = "Идентификатор условия контроля лимитов")
    private long id;

    @ApiModelProperty(notes = "Вид условия контроля лимитов")
    private Integer conditionKind;

    @ApiModelProperty(notes = "Значение условия контроля лимитов")
    private Long conditionValue;

    @ApiModelProperty(notes = "Идентификатор лимита")
    private Long limitId;

}
