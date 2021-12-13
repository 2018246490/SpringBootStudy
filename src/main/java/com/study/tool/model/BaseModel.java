package com.study.tool.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 吕伟林
 * 2021/7/16
 */
@MappedSuperclass
public class BaseModel implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "create_user", updatable = false)
    private Long createUser;//创建人
    @Column(name = "create_time", updatable = false)
    private Date createTime;//创建时间
    @Column(name = "update_user", insertable = false)
    private Long updateUser;//修改人
    @Column(name = "update_time", insertable = false)
    private Date updateTime;//修改时间
    @Column(name = "bdelete")
    private Boolean bdelete;// 是否删除状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getBdelete() {
        return bdelete;
    }

    public void setBdelete(Boolean bdelete) {
        this.bdelete = bdelete;
    }
}
