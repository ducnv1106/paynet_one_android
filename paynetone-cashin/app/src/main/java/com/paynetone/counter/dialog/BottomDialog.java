package com.paynetone.counter.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.paynetone.counter.R;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomDialog extends BottomSheetDialogFragment {
    @BindView(R.id.btn_dismiss)
    TextView btn_dismiss;
    @BindView(R.id.tv_mobile)
    TextView tv_mobile;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_fee)
    TextView tv_fee;
    @BindView(R.id.tv_status)
    TextView tv_status;

    OrderModel mOrder;

    public BottomDialog(OrderModel order) {
        this.mOrder = order;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_bottom, container,false);
        ButterKnife.bind(this, view);

        tv_amount.setText(NumberUtils.formatPriceNumber(mOrder.getAmount()) + "VNĐ");
        tv_fee.setText("0đ");
        tv_mobile.setText(mOrder.getMobileNumber());
        tv_status.setText(Utils.getStatusName(mOrder.getStatus()));

        switch (mOrder.getStatus()){
            case Constants.STATUS_W:
                tv_status.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.order_status_w));
                break;
            case Constants.STATUS_S:
                tv_status.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.order_status_s));
                break;
            case Constants.STATUS_C:
                tv_status.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.order_status_c));
                break;
        }

        btn_dismiss.setOnClickListener(v -> dismiss());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            View view = getView();
            view.post(() -> {
                View parent = (View) view.getParent();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
                bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
                ((View) bottomSheet.getParent()).setBackgroundColor(Color.TRANSPARENT);
            });
        }
    }
}
