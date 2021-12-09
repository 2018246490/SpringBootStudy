package com.escope.study.websocket;

import com.escope.study.model.DemoApiResultMsg;
import com.escope.study.utils.DemoDataUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Iterator;

/**
 * websocket 定时数据发送线程
 * 2021/8/11
 */
public class DemoDateThread implements Runnable{
    @Override
    public void run() {
        while (true){
            Iterator<DemoWebSocket> it = DemoWebSocket.webSocketMap.iterator();
            DemoApiResultMsg msg = new DemoApiResultMsg();
            msg.setStatus("2");
            msg.setData(DemoDataUtil.getDemoData());//制造随机数据
            Gson gson = new GsonBuilder().create();
            while (it.hasNext()){
                DemoWebSocket dws = it.next();
                try {
                    if(dws.getSession().isOpen()){
                        dws.sendMessage(gson.toJson(msg));//发送消息
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
