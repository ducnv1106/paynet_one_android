package com.paynetone.counter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemBranchBinding
import com.paynetone.counter.databinding.ItemHanMucBankBinding
import com.paynetone.counter.model.response.PaynetGetByParentResponse
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.setSingleClick

class BranchAdapter(val mContext: Context,
                    private val listContent:ArrayList<PaynetGetByParentResponse>,
                    val listener:OnClickItemListener) : RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val binding = DataBindingUtil.inflate<ItemBranchBinding>(LayoutInflater.from(mContext), R.layout.item_branch, parent, false)
        return BranchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.binData(listContent[position])
    }
    fun updateData(newContent:ArrayList<PaynetGetByParentResponse>){
        listContent.clear()
        listContent.addAll(newContent)
        notifyDataSetChanged()
    }
    inner class BranchViewHolder(val binding:ItemBranchBinding) : RecyclerView.ViewHolder(binding.rootView){

        fun binData(item : PaynetGetByParentResponse){
            binding.apply {
                tvName.text = item.name
                imageTick.setShowOrHideDrawable(item.isChecked)
                rootView.setSingleClick {
                    listener.onClickItem(item)
                    if (!item.isChecked) {
                        item.isChecked=true
                        resetSelected(this@BranchViewHolder.adapterPosition)
                    }
                }
            }
        }
    }
    fun resetSelected(position: Int) {
        listContent.let {
            notifyItemChanged(position)
            for (i in it.indices) {
                if (i != position && it[i].isChecked) {
                    it[i].isChecked=false
                    notifyItemChanged(i)
                }
            }

        }

    }

    override fun getItemCount(): Int  = listContent.size

    interface OnClickItemListener {
        fun onClickItem(item:PaynetGetByParentResponse)
    }
}