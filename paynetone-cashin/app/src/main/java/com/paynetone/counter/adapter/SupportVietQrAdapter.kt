package com.paynetone.counter.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemSupportVietQrBinding
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.loadImageWithGlideResource
import java.util.concurrent.Executors

class SupportVietQrAdapter(val mContext: Context, val typeSupport:Int): ListAdapter<Int, SupportVietQrAdapter.SupportVietQrHolder>(
    AsyncDifferConfig.Builder<Int>(DIFF_CALLBACK).setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build())  {

    private val listContent by lazy { if (typeSupport == Constants.PAYMENT_TYPE_VIETQR) arrayListOf<Int>(R.drawable.logo_01_abbank,
            R.drawable.logo_02_acb,R.drawable.logo_03_bac_abank,R.drawable.logo_04_bidv,R.drawable.logo_05_bao_vietbank,R.drawable.logo_06_cakebypvbank,
            R.drawable.logo_07_cbbank,R.drawable.logo_08_cimbbank,R.drawable.logo_09_coop_bank,R.drawable.logo_10_dbs,R.drawable.logo_11_dong_abank,
            R.drawable.logo_12_eximbank,R.drawable.logo_13_gpbank,R.drawable.logo_14_hdbank,R.drawable.logo_15_hong_leong_bank,R.drawable.logo_16_hsbc,
            R.drawable.logo_17_ibk,R.drawable.logo_18_viettinbank,R.drawable.logo_19_ivb,R.drawable.logo_20_kbbank,R.drawable.logo_21_kasikorn,R.drawable.logo_22_kienlongbank,
            R.drawable.logo_23_lienvietpostbank,R.drawable.logo_24_mbbank,R.drawable.logo_25_msb,R.drawable.logo_26_namabank,R.drawable.logo_27_ncb,
            R.drawable.logo_28_nong_hyup_bank,R.drawable.logo_29_ocb,R.drawable.logo_30_oceanbank,R.drawable.logo_31_publicbank,R.drawable.logo_32_pvcombank,
            R.drawable.logo_33_scb,R.drawable.logo_34_standardchartered,R.drawable.logo_35_seabank,R.drawable.logo_36_saigonbank,R.drawable.logo_37_shb,
            R.drawable.logo_38_shinhanbank,R.drawable.logo_39_sacombank,R.drawable.logo_40_techcombank,R.drawable.logo_41_tpbank,R.drawable.logo_42_uob,
            R.drawable.logo_43_ubankbypvbank,R.drawable.logo_44_vietabank,R.drawable.logo_45_agribank,R.drawable.logo_46_vietcombank,R.drawable.logo_47_vietcapitalbank,
            R.drawable.logo_48_vib,R.drawable.logo_49_vietbank,R.drawable.logo_50_vpbank,R.drawable.logo_51_vrb,R.drawable.logo_52_wooribank,R.drawable.logo_53_pgbank)
    else arrayListOf<Int>(R.drawable.logo_46_vietcombank,R.drawable.logo_45_agribank,R.drawable.logo_04_bidv,
            R.drawable.logo_18_viettinbank,R.drawable.logo_40_techcombank,R.drawable.logo_01_abbank,R.drawable.logo_02_acb,R.drawable.logo_03_bac_abank,
            R.drawable.logo_54_bidc,R.drawable.logo_47_vietcapitalbank,
            R.drawable.logo_05_bao_vietbank,R.drawable.logo_09_coop_bank,R.drawable.logo_11_dong_abank,R.drawable.logo_12_eximbank,R.drawable.logo_13_gpbank,
            R.drawable.logo_14_hdbank,R.drawable.logo_19_ivb,R.drawable.logo_22_kienlongbank,R.drawable.logo_23_lienvietpostbank,R.drawable.logo_24_mbbank,
            R.drawable.logo_25_msb,R.drawable.logo_27_ncb,R.drawable.logo_26_namabank,R.drawable.logo_29_ocb,R.drawable.logo_30_oceanbank,R.drawable.logo_53_pgbank,
            R.drawable.logo_55_publicbank_vietnam,R.drawable.logo_32_pvcombank,R.drawable.logo_33_scb,R.drawable.logo_37_shb,R.drawable.logo_50_vpbank,
            R.drawable.logo_44_vietabank,R.drawable.logo_49_vietbank)}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportVietQrHolder {
        val binding = DataBindingUtil.inflate<ItemSupportVietQrBinding>(LayoutInflater.from(mContext), R.layout.item_support_viet_qr, parent, false)
        return SupportVietQrHolder(binding)
    }

    fun submitList(){
        this.submitList(listContent)
    }

    override fun onBindViewHolder(holder: SupportVietQrHolder, position: Int) {
        holder.binData(listContent[position])
    }

    override fun getItemCount(): Int = listContent.size


    inner class SupportVietQrHolder(val binding:ItemSupportVietQrBinding) : RecyclerView.ViewHolder(binding.root){

         fun binData(item:Int){
             binding.apply {
                 imgBank.loadImageWithGlideResource(ContextCompat.getDrawable(mContext,item))
             }

        }

    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Int, newItem: Int) =
                oldItem == newItem
        }
    }

}