package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
@ApiModel(value = "ErrorResponse", description = "Ответ об ошибке")
public class ErrorResponse {
    @ApiModelProperty(notes = "Код ошибки")
    private final int errorCode;

    @ApiModelProperty(notes = "Описание ошибки")
    private final String errorDescription;
}
