package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "LimitDTO", description = "Лимит")
public class LimitDTO {

    private long id;

    private String name;

    private String comment;

    private Date startDate;

    private Date endDate;

    private BigDecimal breakConditionValue;

    private CurrencyDTO currency;

    private List<LimitConditionDTO> limitConditionList;
}
