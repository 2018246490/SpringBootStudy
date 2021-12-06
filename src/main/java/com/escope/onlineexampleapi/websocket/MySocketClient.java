package com.escope.onlineexampleapi.websocket;

import com.escope.onlineexampleapi.utils.FileUtil;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * websocket 客户端
 *
 * @author lvweilin
 * 2021/11/29
 */

@WebSocket
public class MySocketClient {

    private final CountDownLatch closeLatch;

    private int index = 1;
    private int dirIndex = 1;

    @SuppressWarnings("unused")
    private Session session;

    public MySocketClient() {
        this.closeLatch = new CountDownLatch(1);
    }

    public boolean await(int duration, TimeUnit unit) throws InterruptedException {
//        return this.closeLatch.await(duration, unit);
        this.closeLatch.await();
        return true;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Got connect: %s%n", session);
        this.session = session;
        try {

//            session.close(StatusCode.NORMAL, "I'm done");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) throws Exception {
        System.out.printf("Got msg: %s%n", msg);

        if (dirIndex > 10) {
            this.index = 1;
            this.dirIndex = 1;
            this.session.close();
            return;
        }

        if (index > 60) {
            dirIndex++;
            index = 1;
        }

        String dirPath = "D:/data/websocket/" + dirIndex;
        System.out.println("文件夹名称：" + dirPath);
        FileUtil.createFile(dirPath, 0);//检查并创建文件夹
        //将信息写入文件系统
        String filePath = dirPath + "/socket" + index + ".json";
        System.out.println("文件名称" + filePath);
        FileUtil.writeFileContent(filePath, msg, 1);
        this.index++;
    }

}
