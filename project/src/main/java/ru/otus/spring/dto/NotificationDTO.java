package ru.otus.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Notificcation", description = "Cooбщение")
public class NotificationDTO {
    @ApiModelProperty(notes = "Код ошибки")
    private Long ntfCode;

    @ApiModelProperty(notes = "Сообщение об ошибке")
    private String message;
}
