package com.study.app.sys.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_operating_log")
public class SysOperatingLog {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_code")
    private String userCode;
    @Column(name = "type")
    private Integer type; //-1删除 0 修改 1新增  10登录系统,11退出系统
    @Column(name = "module")
    private String module;
    @Column(name = "rmk")
    private String rmk;
    @Column(name = "time")
    private Date time;

    @Column(name = "bdelete")
    private Boolean bdelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getBdelete() {
        return bdelete;
    }

    public void setBdelete(Boolean bdelete) {
        this.bdelete = bdelete;
    }
}
