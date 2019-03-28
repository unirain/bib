package com.ylzinfo.bib.core.exception;

import org.springframework.http.HttpStatus;

/********************************************************************************
 *
 * Title: 请求异常
 *
 * Description: 400返回码
 *
 * @author chenlm
 * create date on 2018/12/21
 *
 *******************************************************************************/
public class RequestException extends BaseException {
    public RequestException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public RequestException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public RequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
