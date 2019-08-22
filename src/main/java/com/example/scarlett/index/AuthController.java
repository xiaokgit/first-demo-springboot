package com.example.scarlett.index;

import com.annotation.AopLog;
import com.annotation.AopLogType;
import com.utils.RedisUtil;
import com.utils.jwt.JWTUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: Xiaok
 * @Date: 2019/8/20 14:27
 * @version: 1.0
 * @Description:
 */

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    /**
     * 跳转到登录页面
     * @param request
     * @return
     */
    @AopLog(name = "toLogin", value = "toLogin-value", type = AopLogType.GET)
    @RequestMapping(value = "/toLogin")
    public String toLogin(HttpServletRequest request){
        return "login";
    }


    @AopLog(name = "login", value = "login-value", type = AopLogType.GET)
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean flag = false;
        if(flag){
            String jwtSign = JWTUtils.createdJWT(UUID.randomUUID().toString().replace("-", ""), username, password);
            request.getSession().setAttribute("JWT:Token:sign:"+username, jwtSign);
        }
        //
        RedisUtil.save("ScarlettTest:", "this is login method...");
        return "login";
    }

    @AopLog(name = "logout", value = "logout-value", type = AopLogType.POST)
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "logout";
    }

}
