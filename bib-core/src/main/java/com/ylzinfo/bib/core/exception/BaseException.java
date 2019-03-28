package com.ylzinfo.bib.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/********************************************************************************
 *
 * Title: 基本异常
 *
 * Description: 其他异常必须继承
 *
 * @author chenlm
 * create date on 2018/12/20
 *
 *******************************************************************************/
@Data
public abstract class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BaseException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus =httpStatus;
    }
    public BaseException(HttpStatus httpStatus){
        super(httpStatus.getReasonPhrase());
        this.httpStatus =httpStatus;
    }


}
