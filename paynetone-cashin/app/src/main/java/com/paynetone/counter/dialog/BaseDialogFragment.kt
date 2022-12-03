package com.paynetone.counter.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.paynetone.counter.R
import com.paynetone.counter.utils.DialogUtils
import com.paynetone.counter.utils.autoCleared
import kotlinx.android.synthetic.main.layout_toast.view.*

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    private var _binding: VB by autoCleared()
    val binding: VB get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        loadControlsAndResize(binding)
        return binding.root
    }


    override fun onCreateDialog( savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                this@BaseDialogFragment.onBackPressed()
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

     open fun onBackPressed() {
         dismiss()
    }

    abstract fun initView()

    open fun initStyle(): Int {
        return R.style.FullScreenDialog
    }
    open fun initCancelable() : Boolean{
        return false
    }
    open fun loadControlsAndResize(binding:VB){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setStyle(STYLE_NO_FRAME, initStyle())

    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(initCancelable())
    }


    fun showProgressDialog() {
        DialogUtils.showProgressDialog(activity)
    }

    fun hideProgressDialog() =   DialogUtils.dismissProgressDialog()

    fun toast(message: String) {
        val parent: ViewGroup? = null
        val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val toastView = inflater.inflate(R.layout.layout_toast, parent)
        toastView.messageToast.text = message
        toast.view = toastView
        toast.show()
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB


}
