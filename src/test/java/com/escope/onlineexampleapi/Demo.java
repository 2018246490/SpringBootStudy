package com.escope.onlineexampleapi;

import com.escope.onlineexampleapi.websocket.MySocketClient;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Administrator
 * 2021/11/29
 */
public class Demo {
    public static void main(String[] args) {
        String destUri = "wss://its.sutpc.com/api/yijing/bhw/test/sim/wsHandler/Common/pageid";
        if (args.length > 0) {
            destUri = args[0];
        }
        WebSocketClient client = new WebSocketClient();
        MySocketClient socket = new MySocketClient();
        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            System.out.printf("Connecting to : %s%n", echoUri);
            socket.await(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
