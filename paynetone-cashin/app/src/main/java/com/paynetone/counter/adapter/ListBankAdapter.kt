package com.paynetone.counter.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemHanMucBankBinding
import com.paynetone.counter.model.BankHanMuc
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.handlerCopyText
import com.paynetone.counter.utils.loadImageWithGlideResource
import com.paynetone.counter.utils.setSingleClick
import java.util.concurrent.Executors

class ListBankAdapter(val context: Context,
                      val itemClickListener: (BankHanMuc)->Unit
):ListAdapter<BankHanMuc,ListBankAdapter.ListBankHolder>(
    AsyncDifferConfig.Builder<BankHanMuc>(DIFF_CALLBACK).setBackgroundThreadExecutor(
        Executors.newSingleThreadExecutor()
    ).build()
) {
    private val lisContent by lazy { arrayListOf<BankHanMuc>(
            BankHanMuc( image = R.drawable.logo_04_bidv,
                fullName = context.resources.getString(R.string.str_full_name_bank),
                nameBank = "Ngân hàng Thương mại cổ phần Đầu tư và Phát triển Việt Nam",
                accountNumber = "21210002226222"),
            BankHanMuc( image = R.drawable.logo_18_viettinbank,
                fullName = context.resources.getString(R.string.str_full_name_bank),
                nameBank = "Ngân hàng Viettin – Chi nhánh Ba Đình – PGD Vĩnh Phúc",
                accountNumber = "112678337979"))
//            BankHanMuc(image = R.drawable.logo_23_lienvietpostbank,
//                fullName = context.resources.getString(R.string.str_full_name_bank),
//                nameBank = "Ngân hàng Liên Việt – PGD Hoàng Quốc Việt",
//                accountNumber = "025584650001"))
    }

    fun submitList(){
        submitList(lisContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBankHolder {
        val binding = DataBindingUtil.inflate<ItemHanMucBankBinding>(LayoutInflater.from(context), R.layout.item_han_muc_bank, parent, false)
        return ListBankHolder(binding)
    }

    override fun onBindViewHolder(holder: ListBankHolder, position: Int) {
        val item = currentList[position]
        holder.binData(item)
    }


    inner class ListBankHolder(val binding:ItemHanMucBankBinding) : RecyclerView.ViewHolder(binding.root){

        fun binData(item: BankHanMuc) {

            binding.apply {
                tvFullNamePersonalBank.text = item.fullName
                tvNameBank.text = item.nameBank

                val text = "Tài khoản: <u><font color='#027FFE'>${item.accountNumber}.</font></u>"
                tvAccountNumber.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

                imgLogo.loadImageWithGlideResource(ContextCompat.getDrawable(context,item.image))
                rootView.setSingleClick {
                    itemClickListener(item)
                }
                tvAccountNumber.setOnLongClickListener {
                    tvAccountNumber.handlerCopyText(item.accountNumber,context)
                    return@setOnLongClickListener true
                }

            }

        }

    }
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BankHanMuc>() {
            override fun areItemsTheSame(oldItem: BankHanMuc, newItem: BankHanMuc) =
                oldItem.accountNumber == newItem.accountNumber

            override fun areContentsTheSame(oldItem: BankHanMuc, newItem: BankHanMuc) =
                oldItem == newItem
        }
    }


}