package com.paynetone.counter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParamModel {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Value")
    @Expose
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
