package com.ylzinfo.bib.core.exception;

import org.springframework.http.HttpStatus;

/********************************************************************************
 *
 * Title: 业务异常
 *
 * Description: 无法办理具体业务。通常返回500
 *
 * @author chenlm
 * create date on 2018/12/20
 *
 *******************************************************************************/
public class BusinessException extends BaseException{
    public BusinessException(HttpStatus httpStatus, String message){
        super(httpStatus,message);
    }
    public BusinessException(HttpStatus httpStatus){
        super(httpStatus);
    }
    public BusinessException(String message){
        super(HttpStatus.INTERNAL_SERVER_ERROR,message);
    }
}
