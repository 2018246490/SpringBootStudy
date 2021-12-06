package com.escope.onlineexampleapi.websocket;

import com.escope.onlineexampleapi.model.DemoApiResultMsg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

//安卓端的网络长链接
@ServerEndpoint(value="/demowebsocket")
@Component
@Slf4j
public class DemoWebSocket {

	static {
		//启动 定时消息发送
		DemoDateThread run = new DemoDateThread();
		new Thread(run).start();
	}
	
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	protected static CopyOnWriteArrayList<DemoWebSocket> webSocketMap = new CopyOnWriteArrayList<>();
	

	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;


	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 * @throws EncodeException
	 * @throws IOException 
	 */
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		try{
			DemoApiResultMsg msg = new DemoApiResultMsg();
			msg.setStatus("1");
			msg.setMsg("连接成功！");
			Gson gson = new GsonBuilder().create();
			this.sendMessage(gson.toJson(msg));
			this.webSocketMap.add(this);
		}catch (Exception e){

		}
	}

	/**
	 * 连接关闭调用的方法
	 * @throws IOException 
	 */
	@OnClose
	public void onClose(){
		webSocketMap.remove(this);
		colseConn();
	}
	

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {

	}
	

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 * @throws IOException 
	 */
	@OnError
	public void onError(Session session, Throwable error) throws IOException{
//		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		if(this.session.isOpen()){
			this.session.getBasicRemote().sendText(message);
		}
	}
	
	public void colseConn(){
		this.subOnlineCount();           //在线数减1
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		DemoWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		DemoWebSocket.onlineCount--;
	}

	public Session getSession() {return session;}
	public void setSession(Session session) {this.session = session;}
}
