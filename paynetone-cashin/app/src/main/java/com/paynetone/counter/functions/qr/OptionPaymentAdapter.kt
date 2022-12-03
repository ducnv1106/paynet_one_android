package com.paynetone.counter.functions.qr

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemGroupBankBinding
import com.paynetone.counter.databinding.ItemOptionPaymentBinding
import com.paynetone.counter.databinding.ItemServiceBinding
import com.paynetone.counter.model.PaymentModel
import com.paynetone.counter.model.ServiceModel
import com.paynetone.counter.model.request.GetProviderResponse
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.Constants.*
import com.paynetone.counter.utils.loadImageWithGlide
import com.paynetone.counter.utils.loadImageWithGlideResource
import com.paynetone.counter.utils.setSingleClick

class OptionPaymentAdapter(private val mContext:Context,
                           var listener: OnClickItemListener,
                           private var typeProvider:ProviderEnum,
                           private val listProvider : ArrayList<GetProviderResponse>
                           ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
    companion object{
        const val TYPE_QR = 1
        const val TYPE_SERVICE = 2
        const val TYPE_GROUP_BANK = 3
    }


    private val listContent by lazy {
        when(typeProvider){
            ProviderEnum.BANK -> { arrayListOf<GetProviderResponse>().apply {
                    addAll( listProvider.filter { it.type == TYPE_BANK }.take(7))
                    add(GetProviderResponse(type = TYPE_BANK,name = "Xem tất cả", isActive = "Y", itemGroup = true))
                }
            }
            ProviderEnum.E_WALLET -> listProvider.filter { it.type == TYPE_EWALLET }
            ProviderEnum.PAYMENT_QR -> listProvider.filter { it.type == TYPE_PAYMENT_QR }
            else -> listProvider.filter { it.type == TYPE_GTGT }
        }
    }



    interface OnClickItemListener {
        fun onClickItem(item: GetProviderResponse)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_SERVICE -> {
                val binding = DataBindingUtil.inflate<ItemServiceBinding>(LayoutInflater.from(mContext), R.layout.item_service, parent, false)
                ServiceHolder(binding)
            }
            else->{
                val binding = DataBindingUtil.inflate<ItemOptionPaymentBinding>(LayoutInflater.from(mContext), R.layout.item_option_payment, parent, false)
                OptionPaymentHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ServiceHolder){
            holder.apply {
                this.binData(listContent[position])
            }
        }
        if (holder is OptionPaymentHolder){
            holder.apply {
                this.binData(listContent[position])
            }
        }
    }

    override fun getItemCount(): Int = listContent.size

    override fun getItemViewType(position: Int): Int {
        return when(listContent[position].type){
            TYPE_GTGT -> TYPE_SERVICE
            else ->  TYPE_QR
        }
    }

    inner class OptionPaymentHolder(private val binding:ItemOptionPaymentBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item:GetProviderResponse){
            binding.apply {
                if (item.paymentType ==  Constants.PAYMENT_TYPE_VIETTEL){
                    tvName.text = item.name?.replace(" ","\n")
                }else{
                    tvName.text = item.name
                }

                if (item.itemGroup)
                    imgLogo.loadImageWithGlideResource(ContextCompat.getDrawable(mContext,R.drawable.ic_group_bank))
                else
                    imgLogo.loadImageWithGlide(item.icon ?: "")

                rootView.setSingleClick {
                    listener.onClickItem(item)
                }
                if (item.isActive == PROVIDER_NO_ACTIVE){
                    viewDim.visibility = View.VISIBLE
                    tvName.setTextColor(mContext.resources.getColor(R.color.grey,null))
//                    tvName.alpha = 0.45F
                }
            }
        }
    }

    inner class ServiceHolder(val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: GetProviderResponse){
            binding.apply {
                tvName.text = item.name
                imgLogo.loadImageWithGlide(item.icon ?: "")

                if (item.isActive == PROVIDER_NO_ACTIVE){
                    viewDim.visibility = View.VISIBLE
                    tvName.setTextColor(mContext.resources.getColor(R.color.grey,null))
//                    tvName.alpha = 0.45F
                }
                rootView.setSingleClick {
                    listener.onClickItem(item)
                }
            }

        }

    }
    enum class ProviderEnum {
        BANK,
        E_WALLET,
        PAYMENT_QR,
        SERVICE
    }



}