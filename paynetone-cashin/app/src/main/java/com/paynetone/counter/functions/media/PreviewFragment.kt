package com.paynetone.counter.functions.media

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.paynetone.counter.databinding.FrmPreviewBinding
import com.paynetone.counter.R
import com.paynetone.counter.model.Media
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_MEDIA
import com.paynetone.counter.utils.bottomMargin
import com.paynetone.counter.utils.fitSystemWindows
import com.paynetone.counter.utils.onWindowInsets
import com.paynetone.counter.utils.topMargin


class PreviewFragment : BaseFragmentMedia<FrmPreviewBinding>(R.layout.frm_preview) {

    override val binding: FrmPreviewBinding by lazy { FrmPreviewBinding.inflate(layoutInflater) }
    private var media: Media?=null
    private val args:PreviewFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        args.media.let {
            this.media=it
            binding.apply {
                Glide.with(requireContext()).load(media?.path)
                    .placeholder(R.drawable.progress_animation)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .transform(CenterCrop(), RoundedCorners(1))
                    .into(imagePreview)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adjustInsets()
//        playVideo()
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnSend.setOnClickListener { shareImage() }

    }

    private fun playVideo() {
        try {
            binding.apply {
                getMedia().firstOrNull()?.let {
                    Glide.with(requireContext()).load(it?.uri)
                        .placeholder(R.drawable.progress_animation)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .transform(CenterCrop(), RoundedCorners(1))
                        .into(imagePreview)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * This methods adds all necessary margins to some views based on window insets and screen orientation
     * */
    private fun adjustInsets() {
        activity?.window?.fitSystemWindows()
        binding.btnBack.onWindowInsets { view, windowInsets ->
            view.topMargin = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top
        }
        binding.bgBottom.onWindowInsets { view, windowInsets ->
            view.bottomMargin = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
        }
    }

    private fun shareImage() {
        val  activity = requireActivity() as CameraActivity
        val intent = Intent()
        intent.putExtra(EXTRA_MEDIA,media)
        activity.setResult(RESULT_CODE,intent )
        activity.finish()
    }



    override fun onBackPressed() {
        try {

            view?.let { Navigation.findNavController(it).popBackStack() }
//            viewMode.actionState.value=CameraViewModel.STATE.NOTHING

//            val resolver = requireContext().applicationContext.contentResolver
//            resolver.delete(getMedia().get(0).uri, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object{
        const val RESULT_CODE = 101

    }





}