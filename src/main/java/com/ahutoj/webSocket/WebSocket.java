package com.ahutoj.webSocket;

import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@ServerEndpoint("/websocket/{tableId}")
public class WebSocket {

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="tableId")String code) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(code, session);
        System.out.println("【websocket消息】有新的连接，总数为:"+webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        System.out.println("【websocket消息】连接断开，总数为:"+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("【websocket消息】收到客户端消息:"+message);
    }

    // 此为广播消息
    public void sendAllMessage(String message) {

        for(WebSocket webSocket : webSockets) {
            // System.out.println("群发   【websocket消息】广播消息:"+message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String code, String message) {
        Session session = sessionPool.get(code);
        // System.out.println("单点消息  【websocket消息】 唯一标识" +code);
        // 在发送数据之前先确认 session是否已经打开 使用session.isOpen() 为true 则发送消息

        if (session != null && session.isOpen()) {
            try {
                // System.out.println("单点消息  【websocket消息】广播消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
