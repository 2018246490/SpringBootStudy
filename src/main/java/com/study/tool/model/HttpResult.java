package com.study.tool.model;

/**
 * @Auther  吕伟林
 * @Des 接口返回对象
 * @Date 2021/6/25 11:58
 */
public class HttpResult {

  private Integer code;
  private String msg;
  private Object data;


  public HttpResult() {
    code = 200;
    msg = "success";
  }

  public static HttpResult ok() {
    HttpResult r = new HttpResult();
    return r;
  }

  public static HttpResult ok(Object data) {
    HttpResult r = new HttpResult();
    r.setData(data);
    return r;
  }

  public static HttpResult ok(Object data, String msg) {
    HttpResult r = new HttpResult();
    r.setData(data);
    r.setMsg(msg);
    return r;
  }

  public static HttpResult ok(Integer code, Object data, String msg) {
    HttpResult r = new HttpResult();
    r.setCode(code);
    r.setData(data);
    r.setMsg(msg);
    return r;
  }

  public static HttpResult error() {
    HttpResult r = new HttpResult();
    r.setCode(500);
    r.setMsg("未知异常，请联系管理员");
    return r;
  }

  /**
   * .
   *
   * @param msg .
   */
  public static HttpResult error(String msg) {
    HttpResult r = new HttpResult();
    r.setCode(500);
    r.setMsg(msg);
    return r;
  }

  /**
   * .
   *
   * @param data .
   * @param msg .
   */
  public static HttpResult error(Object data, String msg) {
    HttpResult r = new HttpResult();
    r.setCode(500);
    r.setData(data);
    r.setMsg(msg);
    return r;
  }

  /**
   * .
   *
   * @param code .
   * @param data .
   * @param msg .
   */
  public static HttpResult error(Integer code, Object data, String msg) {
    HttpResult r = new HttpResult();
    r.setCode(code);
    r.setData(data);
    r.setMsg(msg);
    return r;
  }

  public static HttpResult loginError() {
    HttpResult r = new HttpResult();
    r.setCode(-1);
    r.setMsg("请登录！");
    return r;
  }

  public static HttpResult roleError() {
    HttpResult r = new HttpResult();
    r.setCode(50);
    r.setMsg("没有权限访问！");
    return r;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
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
