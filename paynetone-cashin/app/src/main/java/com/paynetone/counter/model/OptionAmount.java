package com.paynetone.counter.model;

public class OptionAmount {
    private int amount;
    private String text;
    private boolean isSelected;

    public OptionAmount(int amount, String text, boolean isSelected) {
        this.amount = amount;
        this.text = text;
        this.isSelected = isSelected;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public OptionAmount() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAmount() {
        return amount;
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
