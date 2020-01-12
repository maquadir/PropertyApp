package com.maq.propertyapp.network

import android.view.View
import com.maq.propertyapp.properties.PropertyData

interface RecyclerViewClickListener {

    fun onRecyclerViewItemClick(view: View, property:PropertyData)
}