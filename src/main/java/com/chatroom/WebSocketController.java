package com.chatroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @Author: Xiaok
 * @Date: 2019/8/20 17:53
 * @version: 1.0
 * @Description:
 */

@Controller
@RequestMapping(value = "/chatroom")
public class WebSocketController {

    @Autowired
    WebSocketServer server;

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password) throws IOException{
        //TODO: 校验密码
        server.sendInfo(username + "进入了聊天室!");
        return username;
    }

    @RequestMapping("/")
    public String toChat(){
        //ModelAndView mav = new ModelAndView("chatroom/chat");
        return "chatroom/chat";
    }

}
