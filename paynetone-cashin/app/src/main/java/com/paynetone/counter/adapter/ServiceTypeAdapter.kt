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
import com.paynetone.counter.databinding.ItemQrOptionBinding
import com.paynetone.counter.databinding.ItemServiceBinding
import com.paynetone.counter.databinding.ItemServiceTypeBinding
import com.paynetone.counter.model.QrOptionModel
import com.paynetone.counter.model.ServiceTypeModel
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.setSingleClick
import java.util.concurrent.Executors

class ServiceTypeAdapter (val context: Context, val listener:OnClickItemListener): ListAdapter<ServiceTypeModel, ServiceTypeAdapter.ServiceTypeHolder>(
    AsyncDifferConfig.Builder<ServiceTypeModel>(DIFF_CALLBACK).setBackgroundThreadExecutor(
        Executors.newSingleThreadExecutor()
    ).build()
) {

    private val listContent by lazy { arrayListOf<ServiceTypeModel>(
        ServiceTypeModel(Constants.SERVICE_TYPE_QR,context.resources.getString(R.string.str_service_qr)),
        ServiceTypeModel(Constants.SERVICE_TYPE_TRA_SAU,context.resources.getString(R.string.str_service_tra_sau),false)
    ) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceTypeHolder {
        val binding = DataBindingUtil.inflate<ItemServiceTypeBinding>(LayoutInflater.from(context), R.layout.item_service_type, parent, false)
        return ServiceTypeHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceTypeHolder, position: Int) {
        val item = currentList[position]
        holder.binData(item)
    }

    fun submitList(){
        this.submitList(listContent)
    }

    fun getList() = this.currentList.toList()

    fun resetCurrentList(){
        listContent.clear()
        listContent.addAll(arrayListOf<ServiceTypeModel>(
            ServiceTypeModel(Constants.SERVICE_TYPE_QR,context.resources.getString(R.string.str_service_qr)),
            ServiceTypeModel(Constants.SERVICE_TYPE_TRA_SAU,context.resources.getString(R.string.str_service_tra_sau),false),
        ))
        this.submitList(listContent)
    }

    fun currentListNonChecked(){
        listContent.clear()
        listContent.addAll(arrayListOf<ServiceTypeModel>(
            ServiceTypeModel(Constants.SERVICE_TYPE_QR,context.resources.getString(R.string.str_service_qr),false),
            ServiceTypeModel(Constants.SERVICE_TYPE_TRA_SAU,context.resources.getString(R.string.str_service_tra_sau),false),
        ))
        this.submitList(listContent)
    }

    fun selectItem(id:String){
        val currentList = this.currentList.toList()
        for (i in currentList.indices){
            if (currentList[i].id==id){
                currentList[i].isChecked = true
                notifyItemChanged(i)
            }
        }
    }

    interface OnClickItemListener {
        fun onClickItem()
    }


    inner class ServiceTypeHolder(val binding: ItemServiceTypeBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: ServiceTypeModel) {

            binding.apply {
                tvName.text = item.name
                imageTick.setShowOrHideDrawable(item.isChecked)

                rootView.setSingleClick {
                    item.isChecked = !item.isChecked
                    notifyItemChanged(this@ServiceTypeHolder.adapterPosition)
                    listener.onClickItem()
                }
            }

        }

    }
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ServiceTypeModel>() {
            override fun areItemsTheSame(oldItem: ServiceTypeModel, newItem: ServiceTypeModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ServiceTypeModel, newItem: ServiceTypeModel) =
                oldItem == newItem
        }
    }
}