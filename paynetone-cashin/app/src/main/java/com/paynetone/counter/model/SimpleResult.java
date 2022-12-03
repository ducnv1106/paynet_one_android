package com.paynetone.counter.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class SimpleResult {
    @SerializedName(value = "code", alternate = "Code")
    String errorCode = "-100";

    @SerializedName(value = "message", alternate = "Message")
    String message  ="";

    @SerializedName("Data")
    String data;
    @Override
    public String toString() {
        return "SimpleResult{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
    public String getData() {
        return data;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleResult that = (SimpleResult) o;

        if (!errorCode.equals(that.errorCode)) return false;
        return message.equals(that.message);

    }

    @Override
    public int hashCode() {
        int result = errorCode.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    public SimpleResult() {
    }

    protected SimpleResult(Parcel in) {
        this.errorCode = in.readString();
        this.message = in.readString();
    }
}
