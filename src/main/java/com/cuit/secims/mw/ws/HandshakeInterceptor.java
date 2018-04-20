package com.cuit.secims.mw.ws;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		System.out.println("++++++++++++++++ HandshakeInterceptor: afterHandshake  ++++++++++++++");
		super.afterHandshake(request, response, wsHandler, ex);
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("++++++++++++++++ HandshakeInterceptor: beforeHandshake  ++++++++++++++"+attributes);
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

}
