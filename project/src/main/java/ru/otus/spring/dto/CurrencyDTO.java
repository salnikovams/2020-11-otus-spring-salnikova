package ru.otus.spring.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CurrencyDTO", description = "Валюта")
public class CurrencyDTO {
    @ApiModelProperty(notes = "Идентификатор валюты", example = "1")
    private Long id;

    @ApiModelProperty(notes = "Наименование валюты", example = "Российский рубль")
    private String name;

    @ApiModelProperty(notes = "ISO Код валюты", example = "643")
    private String isoCode;

    @ApiModelProperty(notes = "ALPHA Код валюты", example = "RUB")
    private String alphaCode;
}
