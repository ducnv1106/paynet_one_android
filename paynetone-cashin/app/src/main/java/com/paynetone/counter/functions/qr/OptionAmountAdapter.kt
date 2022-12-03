package com.paynetone.counter.functions.qr

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemAmountBinding
import com.paynetone.counter.model.OptionAmount
import com.paynetone.counter.utils.NumberUtils
import com.paynetone.counter.utils.setSingleClick

class OptionAmountAdapter(val mContext: Context,
                          val listener: CallBackListener
): RecyclerView.Adapter<OptionAmountAdapter.OptionAmountHolder>(),Filterable {

    private var listContent = arrayListOf<OptionAmount>().apply {
        add(OptionAmount(10000, NumberUtils.formatPriceNumber(10000) + "đ", true))
        add(OptionAmount(20000, NumberUtils.formatPriceNumber(20000) + "đ", false))
        add(OptionAmount(50000, NumberUtils.formatPriceNumber(50000) + "đ", false))
        add(OptionAmount(100000, NumberUtils.formatPriceNumber(100000) + "đ", false))
        add(OptionAmount(200000, NumberUtils.formatPriceNumber(200000) + "đ", false))
        add(OptionAmount(500000, NumberUtils.formatPriceNumber(500000) + "đ", false))
    }

    private var filter = arrayListOf<OptionAmount>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionAmountHolder {
        val binding = DataBindingUtil.inflate<ItemAmountBinding>(
            LayoutInflater.from(mContext),
            R.layout.item_amount,
            parent,
            false
        )
        return OptionAmountHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionAmountHolder, position: Int) {

        holder.binData(listContent[holder.bindingAdapterPosition])

    }

    override fun getItemCount(): Int = listContent.size

    inner class OptionAmountHolder(val binding: ItemAmountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binData(item: OptionAmount) {
            binding.apply {
                tvAmount.text = item.text

                if (item.isSelected) {
                    tvAmount.background =
                        ContextCompat.getDrawable(mContext, R.drawable.bg_card_amount)
                    tvAmount.setTypeface(Typeface.DEFAULT_BOLD)
                } else {
                    tvAmount.background =
                        ContextCompat.getDrawable(mContext, R.drawable.bg_card_amount_default)
                    tvAmount.typeface = Typeface.DEFAULT
                }

                rootView.setSingleClick {
                    if (!item.isSelected) {
                        item.isSelected = true
                        notifyItemChanged(this@OptionAmountHolder.bindingAdapterPosition)
                    }
                    resetSelected(this@OptionAmountHolder.bindingAdapterPosition)
                    listener.onItemClicked(item)
                }
            }
        }

        private fun resetSelected(position: Int) {
            for (i in 0 until listContent.size) {
                if (i != position && listContent[i].isSelected) {
                    listContent[i].isSelected = false
                    notifyItemChanged(i)
                }
            }
        }
    }

    interface CallBackListener {
        fun onItemClicked(item: OptionAmount)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().trim() + "đ"
                filter = if (charSearch.isEmpty()) {
                    for (item in listContent) {
                        item.isSelected = false
                    }
                    listContent
                } else {
                    val resultList = ArrayList<OptionAmount>()
                    for (row in listContent) {
                        row.isSelected = row.text == charSearch
                        resultList.add(row)
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
                        listContent = it as ArrayList<OptionAmount>
                    }
                    notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
