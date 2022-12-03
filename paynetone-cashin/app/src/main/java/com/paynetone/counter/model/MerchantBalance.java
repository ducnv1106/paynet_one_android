package com.paynetone.counter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchantBalance {
    @SerializedName("Balance")
    @Expose
    private long balance;
    @SerializedName("AccountType")
    @Expose
    private String accountType;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
