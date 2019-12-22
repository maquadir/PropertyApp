package com.maq.propertyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_view_item.view.*

class PropertyAdapter(val propertyList: Property): RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        return PropertyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_view_item,parent,false)
        )
    }

    override fun getItemCount() = propertyList.data.listings.size

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {

        val property = propertyList.data.listings[position]

        holder.view.title.text = property.Area
        holder.view.addr1.text = property.Location.Address
        holder.view.addr2.text = property.Location.Address2
//        holder.view.addr3.text = property.Location.State + property.Location.Suburb
//        holder.view.bedcount.text = property.Bedrooms.toString()
//        holder.view.bathcount.text = property.Bathrooms.toString()
//        holder.view.carcount.text = property.Carspaces.toString()
//        holder.view.profile_name.text = property.owner.name + " " +property.owner.lastName

    }


    class PropertyViewHolder(val view:View) : RecyclerView.ViewHolder(view)
}