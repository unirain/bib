package com.ylzinfo.bib.core.exception.handle;


import com.ylzinfo.bib.core.entity.RespEntity;
import com.ylzinfo.bib.core.exception.BusinessException;
import com.ylzinfo.bib.core.exception.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/********************************************************************************
 *
 * Title: 统一异常处理
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/3/27
 * Copyright:Copyright © 2017-2018
 *
 *******************************************************************************/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RespEntity requestErrorHandler(RequestException e) {
        log.error("请求异常",e);
        return new RespEntity<>(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespEntity businessErrorHandler(BusinessException e) {
        log.error("业务处理异常",e);
        return new RespEntity<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        log.error("请求参数不合法!", ex);
        RespEntity respEntity = new RespEntity(HttpStatus.BAD_REQUEST,errors);
        return new ResponseEntity<>(respEntity, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + "参数丢失";

        log.error("--------->请求参数不存在!", ex);

        RespEntity respEntity = new RespEntity(HttpStatus.BAD_REQUEST, error);
        return new ResponseEntity<>(respEntity, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "没有找到url的处理器： " + ex.getHttpMethod() + " " + ex.getRequestURL();
        log.error("--------->404未找到处理器!", ex);
        RespEntity respEntity = new RespEntity(HttpStatus.NOT_FOUND, error);
        return new ResponseEntity<>(respEntity, new HttpHeaders(), status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        log.error("--------->数据绑定错误!", ex);
        RespEntity respEntity = new RespEntity(HttpStatus.BAD_REQUEST, message);
        return new ResponseEntity<>(respEntity, headers, status);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("--------->数据绑定错误!", ex);
        RespEntity respEntity = new RespEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(respEntity, headers, status);
    }


}
