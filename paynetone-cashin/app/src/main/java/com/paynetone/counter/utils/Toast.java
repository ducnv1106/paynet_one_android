package com.paynetone.counter.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paynetone.counter.R;

public class Toast {
    public static final int TIME_SHOW = 2500;

    public static void showToast(Context context, int stringResourceId) {
        if (context == null)
            return;
        android.widget.Toast.makeText(context,context.getText(stringResourceId), android.widget.Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message))
            return;

        android.widget.Toast toast =  android.widget.Toast.makeText(context, "",  android.widget.Toast.LENGTH_LONG);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = inflater.inflate(R.layout.layout_toast, null);
        TextView textView = toastView.findViewById(R.id.messageToast);
        textView.setText(message);
        toast.setView(toastView);
        toast.show();
    }
}
