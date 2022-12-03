package com.paynetone.counter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemHistoryDialogBinding
import com.paynetone.counter.model.response.TranSearchResponse
import com.paynetone.counter.utils.NumberUtils

class HistoryDialogAdapter (val context: Context) : RecyclerView.Adapter<HistoryDialogAdapter.HistoryHolder>() {

    private val listContent by lazy { arrayListOf<TranSearchResponse>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryDialogBinding>(LayoutInflater.from(context), R.layout.item_history_dialog, parent, false)
        return HistoryHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val item = listContent[position]
        holder.binData(item)
    }

    fun updateContent(newContent:ArrayList<TranSearchResponse>){
        listContent.clear()
        listContent.addAll(newContent)
        notifyDataSetChanged()
    }
    fun clearAllContent(){
        listContent.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = listContent.size


    inner class HistoryHolder(val binding: ItemHistoryDialogBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: TranSearchResponse) {

            binding.apply {
                tvRetRefNumber.text = item.retRefNumber
                tvTransDate.text = item.transDate
                tvTransReason.text = item.transReason
                tvAmount.text = NumberUtils.formatPriceNumber(item.transAmount?.toLong() ?: 0L) + "đ"
                tvService.text = "Dịch vụ: ${item.providerAcntName}"

                when(item.returnCode){
                    0 -> {
                        tvReturnCode.text = "Thành công"
                        tvReturnCode. background = ContextCompat.getDrawable(context, R.drawable.order_status_s)
                    }
                    1 -> {
                        tvReturnCode.text = "Đang xử lý"
                        tvReturnCode. background = ContextCompat.getDrawable(context, R.drawable.order_status_w)
                    }
                    else ->{
                        tvReturnCode.text = "Thất bại"
                        tvReturnCode. background = ContextCompat.getDrawable(context, R.drawable.order_status_c)
                    }
                }

            }

        }

    }
}