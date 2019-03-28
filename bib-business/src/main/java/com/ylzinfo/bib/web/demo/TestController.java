package com.ylzinfo.bib.web.demo;

import com.ylzinfo.bib.core.entity.RespEntity;
import com.ylzinfo.bib.core.exception.RequestException;
import com.ylzinfo.bib.core.log.IgnoreLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/********************************************************************************
 *
 * Title: 测试
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/12/21
 *
 *******************************************************************************/
@RestController
@Slf4j
public class TestController {

    @GetMapping("/test/v1/{username}")
    public Object get(@PathVariable("username") String username){
        if (!"chenlm".equals(username)){
            throw new RequestException("用户名错误！");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("username",username);
        return  new  RespEntity<>(HttpStatus.OK,"查询成功",map);
    }

    @PostMapping("/test/v1/model")
    @IgnoreLog
    public RespEntity modelSet(@RequestBody TestModel testModel){
        log.info(testModel.toString());
        return new RespEntity<>(HttpStatus.OK,"查询成功",null);
    }






}
