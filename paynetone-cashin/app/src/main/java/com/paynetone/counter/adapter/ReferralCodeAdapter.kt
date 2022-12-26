package com.paynetone.counter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemReferralCodeBinding
import com.paynetone.counter.model.response.ReferralMerchantResponse
import com.paynetone.counter.model.response.WalletResponse
import com.paynetone.counter.utils.handlerCopyText
import com.paynetone.counter.utils.setSingleClick
import java.util.concurrent.Executors

class ReferralCodeAdapter(val context: Context): ListAdapter<ReferralMerchantResponse, ReferralCodeAdapter.ReferralHolder>(
    AsyncDifferConfig.Builder<ReferralMerchantResponse>(DIFF_CALLBACK).setBackgroundThreadExecutor(
        Executors.newSingleThreadExecutor()
    ).build()
) {
    fun removeAll(){
        submitList(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferralHolder {
        val binding = DataBindingUtil.inflate<ItemReferralCodeBinding>(
            LayoutInflater.from(context),
            R.layout.item_referral_code,
            parent,
            false
        )
        return ReferralHolder(binding)
    }

    override fun onBindViewHolder(holder: ReferralHolder, position: Int) {
        val item = currentList[position]
        holder.binData(item)
    }


    inner class ReferralHolder(val binding: ItemReferralCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binData(item: ReferralMerchantResponse) {
            binding.apply {
                tvTime.text = item.createDate
                tvPhoneNumber.text = item.newMerchantMobile
                tvReferralCode.text = item.referralMerchantCode
                if (item.status=="P"){
                    tvStatus.text = "Thành công"
                }else{
                    tvStatus.text = "Chưa hoàn tất"
                }
                layoutReferralCode.setSingleClick {
//                    itemClickListener(item.referralMerchantCode)
                    tvReferralCode.handlerCopyText(item.referralMerchantCode,context)
                }

            }

        }

    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ReferralMerchantResponse>() {
                override fun areItemsTheSame(
                    oldItem: ReferralMerchantResponse,
                    newItem: ReferralMerchantResponse
                ) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: ReferralMerchantResponse,
                    newItem: ReferralMerchantResponse
                ) =
                    oldItem == newItem
            }
    }
}