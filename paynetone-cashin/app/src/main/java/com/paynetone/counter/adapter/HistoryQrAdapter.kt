package com.paynetone.counter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemHistoryBinding
import com.paynetone.counter.model.OrderModel
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.NumberUtils
import com.paynetone.counter.utils.Utils

class HistoryQrAdapter (val context: Context) : RecyclerView.Adapter<HistoryQrAdapter.HistoryQRHolder>() {

    private val listContent by lazy { arrayListOf<OrderModel>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryQRHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(LayoutInflater.from(context), R.layout.item_history, parent, false)
        return HistoryQRHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryQRHolder, position: Int) {
        val item = listContent[position]
        holder.binData(item)
    }

    fun updateContent(newContent:ArrayList<OrderModel>){
        listContent.clear()
        listContent.addAll(newContent)
        notifyDataSetChanged()
    }
    fun clearAllContent(){
        listContent.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = listContent.size


    inner class HistoryQRHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: OrderModel) {
            binding.apply {
                tvAmount.text = NumberUtils.formatPriceNumber(item.amount.toLong()) + "VNĐ"
                tvDate.text = item.orderDate
                tvCode.text = item.code
                tvStatus.text = Utils.getStatusName(item.status)

                when(item.providerCode){
                    Constants.PROVIDER_ZALO ->{
                        imgLogo.setImageResource(R.drawable.zalopay)
                    }
                    Constants.PROVIDER_VIETTEL->{
                        imgLogo.setImageResource(R.drawable.viettel_money)
                    }
                    Constants.PROVIDER_VN_PAY->{
                        imgLogo.setImageResource(R.drawable.ic_vnpay)
                    }
                    Constants.PROVIDER_MOCA->{
                        imgLogo.setImageResource(R.drawable.ic_moca)
                    }
                    Constants.PROVIDER_VIETQR ->{
                        imgLogo.setImageResource(R.drawable.ic_viet_qr)
                    }
                    else ->{
                        imgLogo.setImageResource(R.drawable.ic_shoppe_pay)
                    }
                }
//                if (item.providerCode == Constants.PROVIDER_ZALO) {
//                    imgLogo.setImageResource(R.drawable.zalopay)
//                } else if (item.providerCode == Constants.PROVIDER_VIETTEL) {
//                    imgLogo.setImageResource(R.drawable.viettel_money)
//                } else if (item.providerCode == Constants.PROVIDER_VN_PAY) {
//                    imgLogo.setImageResource(R.drawable.ic_vnpay)
//                } else if (item.providerCode == Constants.PROVIDER_MOCA) imgLogo.setImageResource(R.drawable.ic_moca) else if (item.providerCode == Constants.PROVIDER_VIETQR) img_logo.setImageResource(
//                    R.drawable.ic_viet_qr
//                ) else imgLogo.setImageResource(R.drawable.ic_shoppe_pay)

                when (item.status) {
                    Constants.STATUS_W -> tvStatus.background = ContextCompat.getDrawable(context, R.drawable.order_status_w)
                    Constants.STATUS_S -> tvStatus.background = ContextCompat.getDrawable(context, R.drawable.order_status_s)
                    Constants.STATUS_C -> tvStatus.background = ContextCompat.getDrawable(context, R.drawable.order_status_c
                    )
                }
            }
        }

    }
}