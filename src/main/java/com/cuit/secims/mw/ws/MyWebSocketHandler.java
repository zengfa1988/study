package com.cuit.secims.mw.ws;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

public class MyWebSocketHandler implements WebSocketHandler{

	private static final Logger log = Logger.getLogger(MyWebSocketHandler.class);
	
	// 保存所有的用户session
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
    
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("connect websocket closed.......");
		users.remove(session);
	}

	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("connect websocket success.......");
		users.add(session);
	}

	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		Map<String, Object> msg = JSONObject.parseObject(message.getPayload().toString(), Map.class);
		log.info("handleMessage......."+message.getPayload()+"..........."+msg);
		
		// 处理消息 msgContent消息内容
        TextMessage textMessage = new TextMessage(msg.get("msgContent").toString(), true);
        // 调用方法（发送消息给所有人）
        sendMsgToAllUsers(textMessage);
	}

	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// 给所有用户发送 信息
    public void sendMsgToAllUsers(WebSocketMessage<?> message) throws Exception{
        for (WebSocketSession user : users) {
            user.sendMessage(message);
        }
    }

}
