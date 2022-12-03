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
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.paynetone.counter.R;
import com.paynetone.counter.adapter.BankDialogAdapter;
import com.paynetone.counter.callback.BankDialogCallback;
import com.paynetone.counter.model.BankModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankBottomDialog extends BottomSheetDialogFragment {
    private final BankDialogCallback mDelegate;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    List<BankModel> bankModels;

    public BankBottomDialog(List<BankModel> bankModels, BankDialogCallback dialogCallback) {
        this.bankModels = bankModels;
        mDelegate = dialogCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_base_bottom, container,false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BankDialogAdapter adapter = new BankDialogAdapter(getContext(), bankModels, (bankModel, position) -> {
            dismiss();
            mDelegate.onResponse(bankModel);
        });

        recyclerView.setAdapter(adapter);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView)view.findViewById(R.id.sv_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
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

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                dismiss();
                break;

        }
    }
}
