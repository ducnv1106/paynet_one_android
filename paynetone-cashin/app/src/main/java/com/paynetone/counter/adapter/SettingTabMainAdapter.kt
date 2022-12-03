package com.paynetone.counter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ItemBranchBinding
import com.paynetone.counter.databinding.ItemSettingTabMainBinding
import com.paynetone.counter.model.TabMainModel

class SettingTabMainAdapter(val mContext: Context,val listContent:ArrayList<TabMainModel>) : DragDropSwipeAdapter<TabMainModel, SettingTabMainAdapter.SettingTabMainHolder>(listContent) {


    inner class SettingTabMainHolder(val itemView:View) : DragDropSwipeAdapter.ViewHolder(itemView){
        val textName: AppCompatTextView = itemView.findViewById(R.id.tv_name)
        val textTab : AppCompatTextView = itemView.findViewById(R.id.tv_tab)

    }

    override fun getViewHolder(itemLayout: View): SettingTabMainAdapter.SettingTabMainHolder = SettingTabMainHolder(itemLayout)

    override fun getViewToTouchToStartDraggingItem(item: TabMainModel, viewHolder: SettingTabMainHolder, position: Int): View?  = null

    override fun onBindViewHolder(item: TabMainModel, viewHolder: SettingTabMainHolder, position: Int) {
        viewHolder.textName.text = item.name
        viewHolder.textTab.text = "Trang ${position+1}"
    }
}