package com.example.test

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.setPadding

class MyImageAdapter(val thumbList: ArrayList<Int>) : BaseAdapter() {
    override fun getCount(): Int = thumbList.size
    override fun getItem(p0: Int): Any = thumbList[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var imageView: ImageView
        if (p1 == null) {
            imageView = ImageView(p2?.context)
            imageView.layoutParams = ViewGroup.LayoutParams(200, 200)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(5)
        } else
            imageView = p1 as ImageView
        imageView.setImageResource(thumbList[p0])
        return imageView
    }
}
