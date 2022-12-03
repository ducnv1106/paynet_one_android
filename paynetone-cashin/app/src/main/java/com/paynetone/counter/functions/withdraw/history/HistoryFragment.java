package com.paynetone.counter.functions.withdraw.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.R;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.response.WithdrawSearchResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HistoryFragment extends ViewFragment<HistoryContract.Presenter> implements HistoryContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.no_data)
    LinearLayout noData;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    HistoryAdapter mAdapter;
    List<WithdrawSearchResponse> mItems;

    public static HistoryFragment getInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.withdrawSearch();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_withdrwaw_history;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        mItems = new ArrayList<>();
        noData.setVisibility(View.GONE);

        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HistoryAdapter(getContext(), mItems);
        recycle.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        iv_back.setOnClickListener(view -> mPresenter.back());

    }

    @Override
    public void showWithdraws(List<WithdrawSearchResponse> withdrawSearchResponses) {
        if (withdrawSearchResponses.size() > 0)
            noData.setVisibility(View.GONE);
        else
            noData.setVisibility(View.VISIBLE);

        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mItems.clear();
        mItems.addAll(withdrawSearchResponses);
        mAdapter.setItems(mItems);
    }
}
