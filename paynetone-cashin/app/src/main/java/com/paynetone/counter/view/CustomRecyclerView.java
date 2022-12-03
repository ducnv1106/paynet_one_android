package com.paynetone.counter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.paynetone.counter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomRecyclerView extends FrameLayout {
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomRecyclerView(Context context) {
        super(context);

        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.custom_recycler_view, this);
        ButterKnife.bind(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }
}
