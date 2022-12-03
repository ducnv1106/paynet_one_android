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
import com.paynetone.counter.databinding.ItemHanMucBankBinding
import com.paynetone.counter.databinding.ItemQrOptionBinding
import com.paynetone.counter.functions.qr.OptionPaymentAdapter
import com.paynetone.counter.model.BankHanMuc
import com.paynetone.counter.model.PaymentModel
import com.paynetone.counter.model.QrOptionModel
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.setSingleClick
import java.util.concurrent.Executors

class QrOptionAdapter (val context: Context,val listener:OnClickItemListener): ListAdapter<QrOptionModel, QrOptionAdapter.QrOptionHolder>(
    AsyncDifferConfig.Builder<QrOptionModel>(DIFF_CALLBACK).setBackgroundThreadExecutor(
        Executors.newSingleThreadExecutor()
    ).build()
) {

    private val listContent by lazy { arrayListOf<QrOptionModel>(
        QrOptionModel(Constants.QR_CODE_TINH,context.resources.getString(R.string.str_qr_code_tinh)),
        QrOptionModel(Constants.QR_CODE_DONG,context.resources.getString(R.string.str_qr_code_dong),false),
    ) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QrOptionHolder {
        val binding = DataBindingUtil.inflate<ItemQrOptionBinding>(LayoutInflater.from(context), R.layout.item_qr_option, parent, false)
        return QrOptionHolder(binding)
    }

    override fun onBindViewHolder(holder: QrOptionHolder, position: Int) {
        val item = currentList[position]
        holder.binData(item)
    }

    fun submitList(){
        this.submitList(listContent)
    }

    fun currentListNonChecked(){
        listContent.clear()
        listContent.addAll(arrayListOf<QrOptionModel>(
            QrOptionModel(Constants.QR_CODE_TINH,context.resources.getString(R.string.str_qr_code_tinh),false),
            QrOptionModel(Constants.QR_CODE_DONG,context.resources.getString(R.string.str_qr_code_dong),false),
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

    fun getList() = this.currentList.toList()

    interface OnClickItemListener {
        fun onClickItem()
    }


    inner class QrOptionHolder(val binding: ItemQrOptionBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: QrOptionModel) {

            binding.apply {
//                tvFullNamePersonalBank.text = item.fullName
                tvName.text = item.name
                imageTick.setShowOrHideDrawable(item.isChecked)

                rootView.setSingleClick {
                    item.isChecked = !item.isChecked
                    notifyItemChanged(this@QrOptionHolder.adapterPosition)
                    listener.onClickItem()
                }
            }

        }

    }
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QrOptionModel>() {
            override fun areItemsTheSame(oldItem: QrOptionModel, newItem: QrOptionModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: QrOptionModel, newItem: QrOptionModel) =
                oldItem == newItem
        }
    }
}