package com.study.app.sys.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_role_button")
public class SysRoleButton {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "menu_code")
    public String menuCode;//菜单code
    @Column(name = "role_id")
    public Long roleId;//角色ID
    @Column(name = "create_flag")
    public String createFlag;//新增
    @Column(name = "update_flag")
    public String updateFlag;//修改
    @Column(name = "auth_flag")
    public String authFlag;//审核/审批
    @Column(name = "del_flag")
    public String delFlag;//删除
    @Column(name = "query_flag")
    public String queryFlag;//查询
    @Column(name = "custom")
    public String custom;//自定义权限
    @Column(name = "print_flag")
    public String printFlag;//打印
    @Column(name = "upload_flag")
    public String uploadFlag;//上传
    @Column(name = "download_flag")
    public String downloadFlag;//下载/导出报表
    @Column(name = "create_user", updatable = false)
    public Long createUser;//录入人ID
    @Column(name = "create_time", updatable = false)
    public Date createTime;//录入时间
    @Column(name = "update_user")
    public Long updateUser;//修改人ID
    @Column(name = "update_time")
    public Date updateTime;//修改时间

    @Column(name = "bdelete")
    private Boolean bdelete;

    @Transient
    private String menuCheck;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getCreateFlag() {
        return createFlag;
    }

    public void setCreateFlag(String createFlag) {
        this.createFlag = createFlag;
    }

    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(String printFlag) {
        this.printFlag = printFlag;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(String downloadFlag) {
        this.downloadFlag = downloadFlag;
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

    public String getMenuCheck() {
        return menuCheck;
    }

    public void setMenuCheck(String menuCheck) {
        this.menuCheck = menuCheck;
    }
}
