package com.paynetone.counter.dialog

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ContactDialogBinding
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.handlerCopyText
import com.paynetone.counter.utils.setSingleClick

class ContactDialog : BaseDialogFragment<ContactDialogBinding>() {


    // The permissions we need for the app to work properly
    private val permissions = mutableListOf(Manifest.permission.CALL_PHONE)

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionGranted()
            } else {
                Toast.showToast(activity,resources.getString(R.string.str_no_call_phone_assess))
            }
        }

    // handler after the permission check
    private fun onPermissionGranted() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + binding.tvPhone.text.toString()))
        startActivity(intent)
    }

    override fun initView(){
        binding.apply {
            tvDone.setSingleClick {
                dismiss()
            }
            tvPhone.setSingleClick {
                if (allPermissionsGranted()){
                    onPermissionGranted()
                }else{
                    permissionRequest.launch(permissions.toTypedArray())
                }
            }
            tvEmail.setOnLongClickListener {
                tvEmail.handlerCopyText(tvEmail.text.toString(),requireContext())
                return@setOnLongClickListener true
            }
            tvFacebook.setOnLongClickListener {
                tvFacebook.handlerCopyText(tvFacebook.text.toString(),requireContext())
                return@setOnLongClickListener true
            }
        }
    }

    override fun initStyle(): Int = R.style.DialogStyle

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = ContactDialogBinding.inflate(inflater,container,false)

    companion object {
        @JvmStatic
        fun getInstance() = ContactDialog().apply {
            arguments = Bundle().apply {

            }
        }
    }


}