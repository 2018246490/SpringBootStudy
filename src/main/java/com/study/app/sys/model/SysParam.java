package com.study.app.sys.model;

import javax.persistence.*;

@Entity
@Table(name = "sys_param")
public class SysParam {
    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;
    @Column(name = "value")
    private String value;
    @Column(name = "name")
    private String name;
    @Column(name = "unit")
    private String unit;

    @Column(name = "bdelete")
    private Boolean bdelete;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getBdelete() {
        return bdelete;
    }

    public void setBdelete(Boolean bdelete) {
        this.bdelete = bdelete;
    }
}
