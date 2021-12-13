package com.study.app.sys.model;

import javax.persistence.*;

@Entity
@Table(name = "sys_menu")
public class SysMenu {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "route")
    private String route;
    @Column(name = "order_num")
    private String orderNum;
    @Column(name = "bdelete")
    private Boolean bdelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getBdelete() {
        return bdelete;
    }

    public void setBdelete(Boolean bdelete) {
        this.bdelete = bdelete;
    }
}
