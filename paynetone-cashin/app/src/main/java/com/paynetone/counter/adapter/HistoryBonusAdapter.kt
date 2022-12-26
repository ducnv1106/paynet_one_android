package com.paynetone.counter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemHistoryAccountBonusBinding
import com.paynetone.counter.model.response.WithdrawSearchResponse
import com.paynetone.counter.utils.NumberUtils
import com.paynetone.counter.utils.Utils
import java.util.concurrent.Executors

class HistoryBonusAdapter(val context: Context): ListAdapter<WithdrawSearchResponse, HistoryBonusAdapter.HistoryBonusHolder>(
    AsyncDifferConfig.Builder<WithdrawSearchResponse>(DIFF_CALLBACK).setBackgroundThreadExecutor(
        Executors.newSingleThreadExecutor()
    ).build()
) {
    fun removeAll(){
        submitList(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryBonusHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryAccountBonusBinding>(
            LayoutInflater.from(context),
            R.layout.item_history_account_bonus,
            parent,
            false
        )
        return HistoryBonusHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryBonusHolder, position: Int) {
        val item = currentList[position]
        holder.binData(item)
    }


    inner class HistoryBonusHolder(val binding: ItemHistoryAccountBonusBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binData(item: WithdrawSearchResponse) {
            try {
                binding.apply {
                    if (item.transCategory == 6){
                        tvAmount.text = "-${NumberUtils.formatPriceNumber(item.amount.toLong())} VND"
                        tvAmount.setTextColor(context.resources.getColor(R.color.close))

                        if (item.shopName.isEmpty()) tvShop.text = "Tài khoản hạn mức"
                        else  tvShop.text = "${item.shopName}"
                        tvShopCode.text = "${item.shopCode}"

                    }else if (item.transCategory == 103){
                        tvAmount.text = "+${NumberUtils.formatPriceNumber(item.amount.toLong())} VND"
                        tvAmount.setTextColor(context.resources.getColor(R.color.colorPrimary))
                        tvShop.text = "${item.transReason}"
                        tvShopCode.visibility = View.GONE
                    }

                    tvDate.text = item.transDate
                    tvCode.text = "${item.retRefNumber}"

                    tvStatus.text = Utils.getStatusWithdrawName(item.returnCode)
                    when (item.returnCode) {
                        0 -> tvStatus.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.order_status_s
                        )
                        1 -> tvStatus.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.order_status_w
                        )
                        2 -> tvStatus.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_status_2
                        )
                        else -> tvStatus.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.order_status_c
                        )
                    }

                }
            }catch (e:Exception){
                e.printStackTrace()
            }


        }

    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<WithdrawSearchResponse>() {
                override fun areItemsTheSame(oldItem: WithdrawSearchResponse, newItem: WithdrawSearchResponse) =
                    oldItem.retRefNumber == newItem.retRefNumber

                override fun areContentsTheSame(oldItem: WithdrawSearchResponse, newItem: WithdrawSearchResponse) =
                    oldItem.retRefNumber == newItem.retRefNumber
            }
    }
}