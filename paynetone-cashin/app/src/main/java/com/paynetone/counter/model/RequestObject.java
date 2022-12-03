package com.paynetone.counter.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class RequestObject {
    @SerializedName("Channel")
    private String channel;
    @SerializedName("Code")
    private String code;
    @SerializedName("Data")
    private String data;
    @SerializedName("Time")
    private String time;
    @SerializedName("Signature")
    private String signature;

    public RequestObject(String channel, String version, String code, String data, String time, String signature) {
        if (!TextUtils.isEmpty(channel))
            this.channel = channel;
        else
            this.channel = "ANDROID";
        this.code = code;
        this.time = time;
        this.data = data;
        this.signature = signature;
    }
}
