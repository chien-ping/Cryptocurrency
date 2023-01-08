package com.crypto.controller;

import com.crypto.exception.ValidationException;
import com.crypto.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/**
 * Controller Exception處理
 * */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * 資料檢查錯誤處理
     * @param e 例外
     * @return 錯誤訊息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseVo handleValidationError(MethodArgumentNotValidException e) {
        List<String> errMsgs = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> new HashMap.SimpleEntry<String,String>(err.getField(),err.getDefaultMessage()))
                .collect(Collectors.groupingBy(entry -> entry.getKey(),
                        mapping(entry -> entry.getValue(),
                                Collectors.joining(","))))
                .entrySet().stream()
                .map(entry -> entry.getKey() + " : " + entry.getValue())
                .collect(toList());
        log.warn("Method Argument Validation error : {}, {}",
                e.getBindingResult().getTarget(), StringUtils.join(errMsgs, ','));
        ResponseVo response = new ResponseVo();
        response.setErrors(errMsgs);
        return response;
    }

    /**
     * 自定義資料檢查錯誤處理
     * @param e 例外
     * @return 錯誤訊息
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseVo handleValidationError(ValidationException e) {
        log.warn("class : {}, method : {}, message : {}",
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getMethodName(),
                e.getMessage());
        ResponseVo response = new ResponseVo();
        response.setErrors(Arrays.asList(messageSource.getMessage(e.getCode(), null, Locale.getDefault())));
        return response;
    }

    /**
     * 其他例外處理
     * @param e 例外
     * @return 錯誤訊息
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseVo handleOtherError(Exception e) {
        log.error("", e);
        ResponseVo response = new ResponseVo();
        response.setErrors(Arrays.asList(messageSource.getMessage("system.error", null, Locale.getDefault())));
        return response;
    }

}
