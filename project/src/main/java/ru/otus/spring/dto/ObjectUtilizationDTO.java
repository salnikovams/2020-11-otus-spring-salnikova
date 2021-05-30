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
@ApiModel(value = "ObjectUtilization", description = "Поток движения средств по объекту")
public class ObjectUtilizationDTO {

    private BigDecimal amount;

    private Long currencyId;

    private String currencyISOCode;

}
