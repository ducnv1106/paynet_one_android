package com.paynetone.counter.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val arrayList: ArrayList<Fragment> = ArrayList()
    private val titleList : MutableList<String> =ArrayList()
    fun addFragment(fragment: Fragment,title: String) {
        arrayList.add(fragment)
        titleList.add(title)
    }

    fun getFragment(position: Int): Fragment? {
        if (position < arrayList.size)
            return arrayList[position]
        return null
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun createFragment(position: Int): Fragment {
        return arrayList[position]
    }
    fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}