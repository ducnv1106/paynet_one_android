package com.paynetone.counter.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.paynetone.counter.R
import com.paynetone.counter.app.PaynetOneApplication
import com.paynetone.counter.databinding.ItemPickCameraBinding
import com.paynetone.counter.databinding.ItemPickImageBinding
import com.paynetone.counter.model.ImagePicker
import com.paynetone.counter.utils.loadImageWithGlide
import com.paynetone.counter.utils.loadImageWithGlideUri
import com.paynetone.counter.utils.resizeLayout
import com.paynetone.counter.utils.setSingleClick

class ImagePickAdapterAvatar(
    private val mContext: Context,
    private val wightItem: Int,
    private val wightCheckBox: Int,
    private val mgCheckBox: Int,
    val itemClickListener: (ImagePicker) ->Unit={ _:ImagePicker->},
    private val textSize: Float
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listImage: ArrayList<ImagePicker> = ArrayList()
    private val TYPE_VIEW_CAMERA=1
    private val TYPE_VIEW_MEDIA=2

    fun insertImagePicker(item:ImagePicker){
        listImage.add(item)
        notifyItemChanged(listImage.size-1)
    }

    inner class CameraVewHolder(val binding: ItemPickCameraBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                rootView.resizeLayout(wightItem, wightItem)
                ivImage.resizeLayout(
                    PaynetOneApplication.getInstance().getSizeWithScale(25.0),
                    PaynetOneApplication.getInstance().getSizeWithScale(25.0))
            }
        }
        fun bind(imagePicker: ImagePicker, position: Int){
            binding.apply {
                rootView.setSingleClick {
                    itemClickListener(imagePicker)
                }
            }

        }
    }

    inner class ImageViewHolder(val binding: ItemPickImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rootView.resizeLayout(wightItem, wightItem)
            binding.rlCheckBox.resizeLayout(wightCheckBox, wightCheckBox)
            (binding.rlCheckBox.layoutParams as ConstraintLayout.LayoutParams).marginEnd =
                mgCheckBox
            (binding.rlCheckBox.layoutParams as ConstraintLayout.LayoutParams).topMargin =
                mgCheckBox
        }

        fun bind(imagePicker: ImagePicker, position: Int) {
            binding.ivImage.loadImageWithGlideUri(Uri.parse(imagePicker.uri))
//            Glide.with(mContext)
//                .load(imagePicker.uri)
//                .apply(RequestOptions().centerCrop())
//                .into(binding.ivImage)

            binding.rootView.setOnClickListener {
                imagePicker.isChecked = !imagePicker.isChecked
                if (imagePicker.isChecked){
                    resetSelected(imagePicker,true)
                    notifyItemChanged(this.bindingAdapterPosition)
                }else{
                    resetSelected(imagePicker,false)
                    notifyItemChanged(this.bindingAdapterPosition)
                }
                itemClickListener(imagePicker)

            }
            binding.rlCheckBox.visibility= View.VISIBLE
            binding.rlCheckBox.setBackgroundResource(if (imagePicker.isChecked) R.drawable.bg_cb_selected_new else R.drawable.bg_cb)

        }


    }

    fun resetSelected(imagePicker: ImagePicker, isAdd: Boolean) {
        listImage.let {
            for (i in it.indices) {
                if (it[i].uri!=imagePicker.uri && it[i].isChecked) {
                    it[i].isChecked=false
                    notifyItemChanged(i)
                }
            }

        }

    }

    private fun from(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType==TYPE_VIEW_MEDIA){
            val binding = ItemPickImageBinding.inflate(layoutInflater, parent, false)
            ImageViewHolder(binding)
        }else{
            val  binding = ItemPickCameraBinding.inflate(layoutInflater,parent,false)
            CameraVewHolder(binding)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return from(parent,viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImagePickAdapterAvatar.ImageViewHolder){
            holder.apply {
                holder.bind(listImage[position], position)
            }

        }
        if (holder is ImagePickAdapterAvatar.CameraVewHolder){
            holder.apply {
                holder.bind(listImage[position], position)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (listImage[position].isCamera){
            TYPE_VIEW_CAMERA
        }else{
            TYPE_VIEW_MEDIA
        }
    }


    override fun getItemCount(): Int {
        return listImage.size
    }
}