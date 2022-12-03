package com.paynetone.counter.functions.outward;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.paynetone.counter.R;
import com.paynetone.counter.functions.history.HistoryAdapter;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.response.GetOutwardResponse;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OutwardFragment extends ViewFragment<OutwardContract.Presenter> implements OutwardContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.no_data)
    LinearLayout noData;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    OutwardAdapter mAdapter;
    List<GetOutwardResponse> mItems;
    String balanceType ="H";
    PaynetModel paynetModel;

    public static OutwardFragment getInstance(){
        return new OutwardFragment();
    }
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getOutward(paynetModel.getMerchantID(),balanceType);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_outward;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        mItems = new ArrayList<>();
        noData.setVisibility(View.GONE);

        balanceType = requireActivity().getIntent().getStringExtra(Constants.AMOUNT_TYPE_BALANCE);
        paynetModel = SharedPref.getInstance(requireContext()).getPaynet();
        mPresenter.getOutward(paynetModel.getMerchantID(),balanceType);

        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new OutwardAdapter(getContext(), mItems);
        recycle.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        iv_back.setOnClickListener(view -> mPresenter.back());
    }

    @Override
    public void showOutward(List<GetOutwardResponse> outwards) {
        if (outwards.size() > 0)
            noData.setVisibility(View.GONE);
        else
            noData.setVisibility(View.VISIBLE);

        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mItems.clear();
        mItems.addAll(outwards);
        mAdapter.setItems(mItems);
    }
}
