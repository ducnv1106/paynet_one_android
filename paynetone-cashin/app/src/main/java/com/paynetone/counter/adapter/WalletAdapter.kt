package com.paynetone.counter.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.BR
import com.paynetone.counter.databinding.ItemWithDrawWalletBinding
import com.paynetone.counter.model.response.WalletResponse
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.setSingleClick
import java.util.concurrent.Executors

class WalletAdapter(var context: Context,val itemClickListener: (walletResponse: WalletResponse) -> Unit) : ListAdapter<WalletResponse, WalletAdapter.WalletHolder>(
    AsyncDifferConfig.Builder<WalletResponse>(WalletDiffUtilCallBack()).setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        val binding = DataBindingUtil.inflate<ItemWithDrawWalletBinding>(LayoutInflater.from(context), R.layout.item_with_draw_wallet, parent, false)
        return WalletHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletHolder, position: Int) {
        val item = getItem(position)
        holder.binData(item,holder.adapterPosition)

    }

    override fun getItemCount(): Int  = currentList.size

    class WalletDiffUtilCallBack : DiffUtil.ItemCallback<WalletResponse>() {

        override fun areItemsTheSame(oldItem: WalletResponse, newItem: WalletResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WalletResponse, newItem: WalletResponse): Boolean {
            return oldItem == newItem
        }

    }
    inner class WalletHolder(val binding: ItemWithDrawWalletBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: WalletResponse, position: Int){
            binding.apply {
                tvNameWallet.text = item.name
                icTickWallet.setShowOrHideDrawable(item.isChecked)
                when(item.id){
                    Constants.WALLET_VIETTEL -> imgWallet.setImageDrawable(context.getDrawable(R.drawable.viettel_money))
                    Constants.WALLET_ZALO -> imgWallet.setImageDrawable(context.getDrawable(R.drawable.zalopay))
                    Constants.WALLET_SHOPEE -> imgWallet.setImageDrawable(context.getDrawable(R.drawable.ic_shoppe_pay))
                    Constants.WALLET_MOMO -> imgWallet.setImageDrawable(context.getDrawable(R.drawable.ic_momo))
                }

                rootView.setSingleClick {
                    itemClickListener(item)
                    if (!item.isChecked) {
                        item.isChecked=true
                        resetSelected(position)
                    }
                }
            }

        }


    }
    fun resetSelected(position: Int) {
        currentList.let {
            notifyItemChanged(position)
            for (i in it.indices) {
                if (i != position && it[i].isChecked) {
                    it[i].isChecked=false
                    notifyItemChanged(i)
                }
            }

        }

    }
}