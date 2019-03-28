package com.ylzinfo.bib.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("返回体")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespEntity<T> implements Serializable {
    @ApiModelProperty("返回状态码")
    private int reqCode;
    @ApiModelProperty("返回时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reqTime;
    @ApiModelProperty("返回信息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;

    public RespEntity(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

    public RespEntity(int reqCode, String message, T t) {
        this.reqCode = reqCode;
        this.message = message;
        this.reqTime = new Date();
        this.data = t;
    }
    public RespEntity(HttpStatus httpStatus, String message, T t) {
        this(httpStatus.value(), message, t);
    }

    public RespEntity(HttpStatus httpStatus, List<String> messageList) {
        this(httpStatus.value(), messageList);
    }
    public RespEntity(int reqCode, List<String> messageList) {
        this.reqCode = reqCode;
        this.message = "";
        for (String s : messageList) {
            this.message = this.message + ";" + s;
        }
        this.reqTime = new Date();
    }


}
