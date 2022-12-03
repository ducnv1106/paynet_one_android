package com.paynetone.counter.functions.service

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.adapter.WalletAdapter
import com.paynetone.counter.databinding.ItemServiceBinding
import com.paynetone.counter.databinding.ItemWithDrawWalletBinding
import com.paynetone.counter.model.ServiceModel
import com.paynetone.counter.model.response.WalletResponse
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.setSingleClick
import java.util.concurrent.Executors

class ServiceAdapter(var context: Context, val itemClickListener: (ServiceModel) -> Unit) : ListAdapter<ServiceModel, ServiceAdapter.ServiceHolder>(

    AsyncDifferConfig.Builder<ServiceModel>(ServiceDiffUtilCallBack()).setBackgroundThreadExecutor(
        Executors.newSingleThreadExecutor()).build()) {

     val listItem by lazy {
        listOf<ServiceModel>(ServiceModel(0,R.drawable.ic_naptien_phone,context.resources.getString(R.string.str_naptien_phone)),
            ServiceModel(1,R.drawable.ic_thue_bao,context.resources.getString(R.string.str_thue_bao_tra_sau)),
                ServiceModel(2,R.drawable.ic_nap_game,context.resources.getString(R.string.str_nap_the_game)),
                ServiceModel(3,R.drawable.ic_topup_data,context.resources.getString(R.string.str_topup_data)),
                ServiceModel(4,R.drawable.ic_tien_dien,context.resources.getString(R.string.str_tien_dien)),
                ServiceModel(5,R.drawable.ic_internet,context.resources.getString(R.string.str_internet)),
                ServiceModel(6,R.drawable.ic_truyen_hinh,context.resources.getString(R.string.str_truyen_hinh)),
                ServiceModel(7,R.drawable.ic_mua_hang_tra_cham,context.resources.getString(R.string.str_mua_hang_tra_cham)),
        )

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHolder {
        val binding = DataBindingUtil.inflate<ItemServiceBinding>(LayoutInflater.from(context), R.layout.item_service, parent, false)
        return ServiceHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceHolder, position: Int) {
        val item = getItem(position)
        holder.binData(item,holder.adapterPosition)

    }

    override fun getItemCount(): Int  = currentList.size

    class ServiceDiffUtilCallBack : DiffUtil.ItemCallback<ServiceModel>() {

        override fun areItemsTheSame(oldItem: ServiceModel, newItem: ServiceModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ServiceModel, newItem: ServiceModel): Boolean {
            return oldItem == newItem
        }

    }
    inner class ServiceHolder(val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: ServiceModel, position: Int){
            binding.apply {
                tvName.text = item.name
                imgLogo.setImageDrawable(context.getDrawable(item.image))

                rootView.setSingleClick {
                    itemClickListener(item)

                }
            }

        }


    }
}