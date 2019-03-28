package com.ylzinfo.bib.web.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/********************************************************************************
 *
 * Title: 
 *
 * Description:
 *
 * @author chenlm
 * create date on 2019/3/28
 *
 *******************************************************************************/
@Data
public class TestModel {
    private String userName;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date requestTime;


}
