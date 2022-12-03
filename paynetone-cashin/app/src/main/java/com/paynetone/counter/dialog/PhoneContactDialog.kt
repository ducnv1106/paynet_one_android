package com.paynetone.counter.dialog

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.adapter.PhoneContactAdapter
import com.paynetone.counter.databinding.PhoneContactDialogBinding
import com.paynetone.counter.model.PhoneContact
import com.paynetone.counter.utils.setSingleClick
import com.paynetone.counter.utils.toast
import kotlinx.android.synthetic.main.layout_toast.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class PhoneContactDialog(val mContext: Context,val itemClick : (PhoneContact) -> Unit) : DialogFragment() {

    private lateinit var binding: PhoneContactDialogBinding
    private lateinit var flow: Flow<PhoneContact>
    private val jobInsertContactPhone = CoroutineScope(Dispatchers.Main)
    private val adapter by lazy { PhoneContactAdapter(requireContext(), arrayListOf()) {
            itemClick(it)
            dismiss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.phone_contact_dialog,container,false)
        binding.lifecycleOwner=this
        initView()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setStyle(STYLE_NO_FRAME, R.style.FullScreenDialogListBankQR)

    }

    fun initView() {
        binding.apply {
            ivBack.setSingleClick {
                dismiss()
            }

        }
        initAdapter()
        listenerSearchView()
        if (allPermissionsGranted()) {
            onPermissionGranted()
        } else {
            permissionRequest.launch(permissions.toTypedArray())
        }


    }
    private fun listenerSearchView(){
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotBlank()){
                    this@PhoneContactDialog.adapter.filter.filter(s)
                } else {
                    Handler(Looper.myLooper()!!).postDelayed({
                        this@PhoneContactDialog.adapter.notifyAllData()
                    },300L)

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    // The permissions we need for the app to work properly
    private val permissions = mutableListOf(Manifest.permission.READ_CONTACTS)

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionGranted()
            } else {
                toast(resources.getString(R.string.str_no_memory_assess),requireContext())
            }
        }

    // handler after the permission check
    private fun onPermissionGranted() {
        getPhoneContacts()
        jobInsertContactPhone.launch {
            flow.collect {
               adapter.insertItem(it)
            }
        }
    }

    private fun getPhoneContacts() {
        flow = flow {
            val uriExternal = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val sortOder = "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
            val cursor: Cursor? =
                activity?.contentResolver?.query(uriExternal, null, null, null, sortOder)
            try {
                cursor?.let {
                    cursor.moveToFirst()
                    do {
                        val phoneNumber: String =
                            it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val name =
                            it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                        val phoneContact = PhoneContact(name, phoneNumber)
                        emit(phoneContact)
                    } while (cursor.moveToNext())
                    cursor.close()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }
    private fun initAdapter(){
        binding.apply {
            recycleView.adapter = adapter
//            recycleView.getRecycledViewPool().setMaxRecycledViews(TYPE_CAROUSEL, 0);

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        try {
            if (jobInsertContactPhone.isActive) jobInsertContactPhone.cancel() // stop coroutine
            flow.cancellable()  // stop flow
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}