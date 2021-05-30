package ru.otus.spring.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.LimitRequestDTO;
import ru.otus.spring.dto.LimitDTO;
import ru.otus.spring.exception.InputParameterException;
import ru.otus.spring.service.LimitService;
import java.util.List;

@RestController
@RequestMapping("/limit")
public class LimitController {

    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping
    @ApiOperation("Создание лимита")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = LimitDTO.class),
            @ApiResponse(code = 422, message = "API Exception", response = InputParameterException.class),
            @ApiResponse(code = 500, message = "Internal Server Error")}
    )
    public ResponseEntity<LimitDTO> createLimit(
            @RequestBody
            @ApiParam(name = "limit", value = "Атрибуты Лимита", required = true)
                    LimitRequestDTO limit
    ) throws InputParameterException {
        return ResponseEntity.status(HttpStatus.CREATED).body(limitService.createLimit(limit));
    }


    @GetMapping
    @ApiOperation(value = "Поиск всех лимитов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LimitDTO.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Server Error")}
    )
    public ResponseEntity<List<LimitDTO>> getLimits() {
        return ResponseEntity.ok(limitService.getAllLimits());
    }


    @GetMapping("/{limitId}")
    @ApiOperation("Поиск лимита по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LimitDTO.class),
            @ApiResponse(code = 422, message = "API Exception", response = InputParameterException.class),
            @ApiResponse(code = 500, message = "Internal Server Error")}
    )
    public ResponseEntity<LimitDTO> getLimitById(
            @ApiParam(name = "limitId", value = "Идентификатор лимита", required = true)
            @PathVariable("limitId") Long limitId
    ) throws InputParameterException {
        return ResponseEntity.ok(limitService.getLimitById(limitId));
    }

    @DeleteMapping("/{limitId}")
    @ApiOperation("Удаление Лимита")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "API Exception", response = InputParameterException.class),
            @ApiResponse(code = 500, message = "Internal Server Error")}
    )
    public ResponseEntity<LimitDTO> deleteLimit(
            @ApiParam(name = "limitId", value = "Идентификатор Лимита", required = true)
            @PathVariable("limitId") Long limitId) throws InputParameterException {
        limitService.deleteLimitById(limitId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{limitId}")
    @ApiOperation("Изменение параметров Лимита")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LimitDTO.class),
            @ApiResponse(code = 422, message = "API Exception", response = InputParameterException.class),
            @ApiResponse(code = 500, message = "Internal Server Error")}
    )
    public ResponseEntity<LimitDTO> updateLimit(
            @ApiParam(name = "limitId", value = "Идентификатор Лимита", required = true)
            @PathVariable("limitId") Long limitId,
            @RequestBody
            @ApiParam(name = "limit", value = "Атрибуты Лимита", required = true)
                    LimitRequestDTO limit
    ) throws InputParameterException {
        return ResponseEntity.ok(limitService.updateLimit(limitId, limit));
    }


}
