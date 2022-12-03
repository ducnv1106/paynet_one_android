package com.paynetone.counter.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemOptionPaymentBinding
import com.paynetone.counter.databinding.ItemServiceBinding
import com.paynetone.counter.model.request.GetProviderResponse
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.loadImageWithGlide
import com.paynetone.counter.utils.loadImageWithGlideResource
import com.paynetone.counter.utils.setSingleClick
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class ListBankQrAdapter(private val mContext: Context,
                        var listener: OnClickItemListener,
                        private val listProvider : ArrayList<GetProviderResponse>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    private val listContent by lazy { listProvider.filter { it.type == Constants.TYPE_BANK } }

    private var filter = listOf<GetProviderResponse>()

    private var filterResult = arrayListOf<GetProviderResponse>().apply {
        addAll(listContent)
    }

    fun notifyAllData(){
        try {
            filterResult.clear()
            filterResult.addAll(listContent)
            notifyDataSetChanged()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    interface OnClickItemListener {
        fun onClickItem(item: GetProviderResponse)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemOptionPaymentBinding>(LayoutInflater.from(mContext), R.layout.item_option_payment, parent, false)
        return OptionPaymentHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OptionPaymentHolder){
            holder.apply {
                this.binData(filterResult[position])
            }
        }
    }

    override fun getItemCount(): Int = filterResult.size


    inner class OptionPaymentHolder(private val binding: ItemOptionPaymentBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: GetProviderResponse){
            binding.apply {
                if (item.paymentType ==  Constants.PAYMENT_TYPE_VIETTEL){
                    tvName.text = item.name?.replace(" ","\n")
                }else{
                    tvName.text = item.name
                }

                if (item.itemGroup)
                    imgLogo.loadImageWithGlideResource(ContextCompat.getDrawable(mContext, R.drawable.ic_group_bank))
                else
                    imgLogo.loadImageWithGlide(item.icon ?: "")

                rootView.setSingleClick {
                    listener.onClickItem(item)
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return  object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().trim()
                filter = if (charSearch.isEmpty()) {
                    listContent
                } else {
                    val resultList = ArrayList<GetProviderResponse>()
                    for (row in listContent) {
                        val covertString= convertToString(row.name ?:"")
                        covertString?.let { name->
                            if (name.contains(convertToString(charSearch) ?: "")) resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filter
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                try {
                    results?.values.let {
                        filterResult=it as ArrayList<GetProviderResponse>
                    }
                    notifyDataSetChanged()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            fun convertToString(str: String?): String? {
                try {
                    val temp: String = Normalizer.normalize(str, Normalizer.Form.NFD)
                    val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                    return pattern.matcher(temp).replaceAll("").toLowerCase(Locale.ROOT).replace("đ", "d")
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                return null
            }
        }
    }

}