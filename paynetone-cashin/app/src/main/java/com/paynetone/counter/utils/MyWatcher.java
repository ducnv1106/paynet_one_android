package com.paynetone.counter.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MyWatcher implements TextWatcher {
    private EditText editText;
    private int selPos;
    private String oldString, newString;
    public MyWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        selPos = editText.getSelectionStart();
        oldString = myFilter(s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            editText.removeTextChangedListener(this);
            newString = myFilter(s.toString());
            editText.setText(newString);
            int newPos = selPos + (newString.length() - oldString.length());
            if (newPos < 0) newPos = 0;
            editText.setSelection(newPos);
            editText.addTextChangedListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String myFilter(String s) {
        String digits;
        digits = s.replaceAll("[!@#$%&*()_+=^|?{}.;\\[\\]~-]", "");
        if (s.equals("")) return "";
        return digits;
    }
}
