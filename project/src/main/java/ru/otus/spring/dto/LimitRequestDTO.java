package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Limit", description = "Лимит")
public class LimitRequestDTO {

    @ApiModelProperty(notes = "Наименование", example = "Name")
    private String name;

    @ApiModelProperty(notes = "Комментарий", example = "Comment")
    private String comment;

    @ApiModelProperty(notes = "Дата начала действия лимита")
    private Date startDate;

    @ApiModelProperty(notes = "Дата окончания действия лимита")
    private Date endDate;

    @ApiModelProperty(notes = "Значение лимита", example = "1.0")
    private BigDecimal breakConditionValue;

    @ApiModelProperty(notes = "Идентификатор валюты", example = "1")
    private Long currencyId;

    @ApiModelProperty(notes = "ISO код валюты", example = "643")
    private String currencyISOCode;

    @ApiModelProperty(notes = "Список условий поиска по добавляемым лимитам")
    private List<LimitConditionCreateDTO> limitConditionList;
}
