package com.chatroom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Xiaok
 * @Date: 2019/8/20 17:53
 * @version: 1.0
 * @Description:
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {

    //静态变量，统计当前在线人数
    private static Integer onlineCount = 0;
    //创建一个线程安全的map
    private static Map<String, WebSocketServer> users = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //放入map中的key,用来表示该连接对象
    private String username;

    /**
     * 连接成功调用的方法
     * @param session
     * @param username
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String username){
        this.session = session;
        this.username = username;
        users.put(username, this); //加入map中,为了测试方便使用username做key
        addOnlineCount();
        log.info(username+"加入！当前在线人数为" + getOnlineCount());
        try {
            this.session.getBasicRemote().sendText("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose(){
        users.remove(this.username); //从map中移除
        subOnlineCount();
        log.info("一个连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 出现异常是调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.error("发生错误 session: "+session);
        error.printStackTrace();
    }

    /**
     * 接受到客户端消息触发
     */
    @OnMessage
    public void onMessage(String message){
        log.info("来自客户端消息："+message);
        try{
            if(StringUtils.isEmpty(message)){
                return;
            }
            //如果给所有人发消息携带@all,给特定人发消息携带@xxx@xxx#message
            String[] split = message.split("#");
            if(split.length > 1){
                String[] userArr = split[0].split("@");
                if(userArr.length < 2){
                    return;
                }
                String firstUser = userArr[1].trim();
                if(StringUtils.isEmpty(firstUser) || "all".equals(firstUser.toLowerCase())){
                    String msg = username + ": " + split[1];
                    sendInfo(msg); //群发消息
                }else{
                    //给特定人发消息
                    for(String user : userArr){
                        if(!StringUtils.isEmpty(user.trim())){
                            sendMessageToSomeBody(user.trim(), split[1]);
                        }
                    }
                }
            }else{
                //群发
                sendInfo(username + ": " + message);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public void sendInfo(String message) throws IOException{
        for(WebSocketServer user : users.values()){
            try {
                user.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                continue;
            }
        }
    }

    /**
     * 发消息给某人
     * @param username
     * @param message
     * @throws IOException
     */
    public void sendMessageToSomeBody(String username, String message) throws IOException{
        if(users.get(username) == null){
            return;
        }
        users.get(username).session.getBasicRemote().sendText(message);
        this.session.getBasicRemote().sendText(this.username + "@" + username + ": " + message);
    }


    /**
     * 在线人数增加
     */
    public static synchronized void addOnlineCount(){
        WebSocketServer.onlineCount++;
    }

    /**
     * 获取在线人数
     * @return
     */
    public static synchronized Integer getOnlineCount(){
        return WebSocketServer.onlineCount;
    }

    /**
     * 在线人数减少
     */
    public static synchronized void subOnlineCount(){
        WebSocketServer.onlineCount--;
    }
}
