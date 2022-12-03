package com.paynetone.counter.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemPhoneContactBinding
import com.paynetone.counter.model.PhoneContact
import com.paynetone.counter.model.request.GetProviderResponse
import com.paynetone.counter.utils.setSingleClick
import java.text.Normalizer
import java.util.*
import java.util.concurrent.Executors
import java.util.regex.Pattern
import kotlin.math.log

class PhoneContactAdapter(
    var context: Context,
    private val listContent: ArrayList<PhoneContact>,
    val itemClickListener: (item: PhoneContact) -> Unit
) : RecyclerView.Adapter<PhoneContactAdapter.PhoneContactHolder>(),Filterable {

    private var textHeader : MutableMap<Int,String> = mutableMapOf()

    private var filter = arrayListOf<PhoneContact>()

    private var filterResult = arrayListOf<PhoneContact>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneContactHolder {
        val binding = DataBindingUtil.inflate<ItemPhoneContactBinding>(
            LayoutInflater.from(context),
            R.layout.item_phone_contact,
            parent,
            false
        )
        return PhoneContactHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneContactHolder, position: Int) {
        val item = filterResult[holder.bindingAdapterPosition]
        holder.binData(item)
    }

    inner class PhoneContactHolder(val binding: ItemPhoneContactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binData(item: PhoneContact) {
            try {
                binding.apply {
                    tvName.text = item.name
                    tvNumberPhone.text = item.phone
                    imgLogo.apply {
                        text = item.name?.get(0).toString()
                        setTextColor(item.color)
                    }

                    rootView.setSingleClick {
                        itemClickListener(item)
                    }

                    val positionHeader = textHeader.filter { (key,value)->
                        value==item.name?.get(0).toString()
                    }.map { (i, _)->i }.singleOrNull()

                    if (positionHeader == null){
                        item.isSelected = true
                        tvHeader.text = item.name?.get(0).toString()
                        layoutHeader.visibility = View.VISIBLE
                        textHeader[this@PhoneContactHolder.bindingAdapterPosition] =
                            item.name?.get(0).toString()
                    }else{
                        layoutHeader.visibility = View.GONE
                    }

                    positionHeader?.let {
                        if (item.isSelected){
                            tvHeader.text = item.name?.get(0).toString()
                            layoutHeader.visibility = View.VISIBLE
                        }

                    }

                }

            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }

    fun insertItem(item: PhoneContact) {
        try {
            listContent.add(item)
            filterResult.add(item)
            notifyItemChanged(listContent.size-1)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun notifyAllData(){
        try {
            textHeader.clear()
            listContent.forEach {
                it.isSelected = false
            }
            filterResult.clear()
            filterResult.addAll(listContent)
            notifyDataSetChanged()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int = filterResult.size

    override fun getFilter(): Filter {
        return  object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().trim()
                filter = if (charSearch.isEmpty()) {
                    arrayListOf<PhoneContact>().apply {
                        addAll(listContent)
                    }
                } else {
                    val resultList = ArrayList<PhoneContact>()
                    for (row in listContent)   {
                        row.isSelected = false
                        val namePersonal= convertToString(row.name ?:"")
                        val numberPhone= convertToString(row.name ?:"")

                        if (numberPhone != null ){
                            if (numberPhone.contains(charSearch) ) {
                                resultList.add(row)
                                continue
                            }

                        }
                        if (namePersonal !=null ){
                            if (namePersonal.contains(convertToString(charSearch) ?: "") ) resultList.add(row)

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
                        textHeader.clear()
                        filterResult=it as ArrayList<PhoneContact>
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