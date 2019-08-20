package com.example.scarlett.index;

import com.annotation.AopLog;
import com.annotation.AopLogType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Xiaok
 * @Date: 2019/8/20 10:42
 * @version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @AopLog(name = "index", value = "index-value", type = AopLogType.GET)
    @RequestMapping(value = "/")
    @ResponseBody
    public String index(HttpServletRequest request){
        return "index string";
    }

}
