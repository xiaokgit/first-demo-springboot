package com.example.scarlett.index;

import com.annotation.AopLog;
import com.annotation.AopLogType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author: Xiaok
 * @Date: 2019/8/20 14:27
 * @version: 1.0
 * @Description:
 */

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @AopLog(name = "login", value = "login-value", type = AopLogType.GET)
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user", new Date());
        return "login";
    }

    @AopLog(name = "logout", value = "logout-value", type = AopLogType.POST)
    @RequestMapping(value = "logout")
    @ResponseBody
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "logout";
    }

}
