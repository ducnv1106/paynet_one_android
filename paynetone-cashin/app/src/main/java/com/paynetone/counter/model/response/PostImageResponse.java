package com.paynetone.counter.model.response;

import com.google.gson.annotations.SerializedName;

public class PostImageResponse {
    @SerializedName("FileName")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
