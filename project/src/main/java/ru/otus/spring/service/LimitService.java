package ru.otus.spring.service;

import ru.otus.spring.dto.LimitRequestDTO;
import ru.otus.spring.dto.LimitDTO;
import ru.otus.spring.dto.NotificationDTO;
import ru.otus.spring.dto.ObjectToCheckRequestDTO;
import ru.otus.spring.exception.InputParameterException;

import java.util.List;

public interface LimitService {
    public LimitDTO createLimit(LimitRequestDTO limitDTO) throws InputParameterException;
    public LimitDTO updateLimit(Long id, LimitRequestDTO limitDTO) throws InputParameterException;
    public void deleteLimitById(Long id)throws InputParameterException;
    public LimitDTO getLimitById(Long id)throws InputParameterException;
    public List<LimitDTO> getAllLimits();
    public NotificationDTO checkLimitByParam(ObjectToCheckRequestDTO checkRequest)throws InputParameterException;
}
