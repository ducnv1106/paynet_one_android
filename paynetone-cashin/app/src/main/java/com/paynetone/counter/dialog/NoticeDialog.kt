package com.paynetone.counter.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.paynetone.counter.R
import com.paynetone.counter.utils.setSingleClick

class NoticeDialog(context: Context) : Dialog(context, R.style.dialogWithoutBox) {
    private var isCancelAble = false
    private lateinit var listener: OnListenerDialog
    fun show(message: String?, cancelAble: Boolean, listener: OnListenerDialog) {
        try {
            isCancelAble = cancelAble
            setCancelable(cancelAble)
            this.listener = listener
            (findViewById<View>(R.id.tv_title) as AppCompatTextView).text = message
            show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        setContentView(R.layout.notice_dialog)
        val displayMetrics = context.resources.displayMetrics
        val vContainer = findViewById<View>(R.id.rlContent)
        vContainer.setSingleClick { if (isCancelAble) dismiss() }
        findViewById<View>(R.id.btn_no).setSingleClick {
            listener.onClickNo()
            dismiss()
        }
        findViewById<View>(R.id.btn_yes).setSingleClick {
            dismiss()
            listener.onClickYes()

        }
    }

    interface OnListenerDialog{
        fun onClickYes()
        fun onClickNo()
    }
}