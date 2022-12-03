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
import com.paynetone.counter.databinding.ItemFormalityTypeBinding
import com.paynetone.counter.databinding.ItemQrOptionBinding
import com.paynetone.counter.databinding.ItemServiceBinding
import com.paynetone.counter.databinding.ItemServiceTypeBinding
import com.paynetone.counter.model.FormalityTypeModel
import com.paynetone.counter.model.QrOptionModel
import com.paynetone.counter.model.ServiceTypeModel
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.setSingleClick
import java.util.concurrent.Executors

class FormalityTypeAdapter (val context: Context, val listener:OnClickItemListener): ListAdapter<FormalityTypeModel, FormalityTypeAdapter.FormalityTypeHolder>(
    AsyncDifferConfig.Builder<FormalityTypeModel>(DIFF_CALLBACK).setBackgroundThreadExecutor(
        Executors.newSingleThreadExecutor()
    ).build()
) {

    private val listContent by lazy { arrayListOf<FormalityTypeModel>(
        FormalityTypeModel(Constants.FORMALITY_TYPE_ONLINE,context.resources.getString(R.string.str_formality_online)),
        FormalityTypeModel(Constants.FORMALITY_TYPE_OFFLINE,context.resources.getString(R.string.str_formality_offline),false)
    ) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormalityTypeHolder {
        val binding = DataBindingUtil.inflate<ItemFormalityTypeBinding>(LayoutInflater.from(context), R.layout.item_formality_type, parent, false)
        return FormalityTypeHolder(binding)
    }

    override fun onBindViewHolder(holder: FormalityTypeHolder, position: Int) {
        val item = currentList[position]
        holder.binData(item)
    }

    fun submitList(){
        this.submitList(listContent)
    }

    fun getList() = this.currentList.toList()

    fun resetCurrentList(){
        listContent.clear()
        listContent.addAll(arrayListOf<FormalityTypeModel>(
            FormalityTypeModel(Constants.FORMALITY_TYPE_ONLINE,context.resources.getString(R.string.str_formality_online)),
            FormalityTypeModel(Constants.FORMALITY_TYPE_OFFLINE,context.resources.getString(R.string.str_formality_offline),false),
        ))
        this.submitList(listContent)
    }

    fun currentListNonChecked(){
        listContent.clear()
        listContent.addAll(arrayListOf<FormalityTypeModel>(
            FormalityTypeModel(Constants.FORMALITY_TYPE_ONLINE,context.resources.getString(R.string.str_formality_online),false),
            FormalityTypeModel(Constants.FORMALITY_TYPE_OFFLINE,context.resources.getString(R.string.str_formality_offline),false),
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


    inner class FormalityTypeHolder(val binding: ItemFormalityTypeBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: FormalityTypeModel) {

            binding.apply {
                tvName.text = item.name
                imageTick.setShowOrHideDrawable(item.isChecked)

                rootView.setSingleClick {
                    item.isChecked = !item.isChecked
                    notifyItemChanged(this@FormalityTypeHolder.adapterPosition)
                    listener.onClickItem()
                }
            }

        }

    }
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FormalityTypeModel>() {
            override fun areItemsTheSame(oldItem: FormalityTypeModel, newItem: FormalityTypeModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FormalityTypeModel, newItem: FormalityTypeModel) =
                oldItem == newItem
        }
    }
}