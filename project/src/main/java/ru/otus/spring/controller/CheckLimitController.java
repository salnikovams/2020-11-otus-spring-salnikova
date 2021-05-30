package ru.otus.spring.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.NotificationDTO;
import ru.otus.spring.dto.ObjectToCheckRequestDTO;
import ru.otus.spring.exception.InputParameterException;
import ru.otus.spring.service.LimitService;

@RestController
@RequestMapping("/checkLimit")
public class CheckLimitController {

    private final LimitService limitService;

    public CheckLimitController(LimitService limitService) {
        this.limitService = limitService;
    }


    @PutMapping
    @ApiOperation(value = "Проверка объекта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = NotificationDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")}
    )
    public ResponseEntity<NotificationDTO> getLimits(@RequestBody
                                                     @ApiParam(name = "request", value = "Атрибуты Объекта", required = true)
                                                                 ObjectToCheckRequestDTO request)throws InputParameterException {
        return ResponseEntity.ok(limitService.checkLimitByParam(request));
    }

}
