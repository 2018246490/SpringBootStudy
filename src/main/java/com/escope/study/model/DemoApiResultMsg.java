package com.escope.study.model;

/**
 * lvweilin
 * 在线示例api 返回消息体
 * 2021/8/11
 */
public class DemoApiResultMsg {
    private String status; //1提示消息， 2数据消息
    private String msg;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
