package com.paynetone.counter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemManagerHanMucBinding
import com.paynetone.counter.model.PaynetGetBalanceByIdResponse
import com.paynetone.counter.model.PhoneContact
import com.paynetone.counter.utils.NumberUtils
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.setSingleClick

class ManagerHanMucAdapter(val mContext:Context,
                           val listContent: ArrayList<PaynetGetBalanceByIdResponse>,
                           val itemClickListener: (item: PaynetGetBalanceByIdResponse,amount:String) -> Unit) : RecyclerView.Adapter<ManagerHanMucAdapter.ManagerHanMucViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerHanMucViewHolder {
        val binding = DataBindingUtil.inflate<ItemManagerHanMucBinding>(LayoutInflater.from(mContext), R.layout.item_manager_han_muc, parent, false)
        return ManagerHanMucViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ManagerHanMucViewHolder, position: Int) {
        holder.binData(listContent[position])
    }
    fun updateData(newContent:List<PaynetGetBalanceByIdResponse>){
        listContent.clear()
        listContent.addAll(newContent)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = listContent.size


    inner class ManagerHanMucViewHolder(val binding:ItemManagerHanMucBinding) : RecyclerView.ViewHolder(binding.root){
        fun binData(item : PaynetGetBalanceByIdResponse){
            binding.apply {
                if (SharedPref.getInstance(mContext).isMerchantAdmin){
                    btnNapHanMuc.visibility = View.VISIBLE
                }

                tvCode.text = item.code
                tvStore.text = item.name
                val amount = NumberUtils.formatPriceNumber(item.amount.toLong()) + " VND"
                tvAmount.text = amount

                btnNapHanMuc.setSingleClick {
                    itemClickListener(item,amount)
                }

            }
        }

    }
}