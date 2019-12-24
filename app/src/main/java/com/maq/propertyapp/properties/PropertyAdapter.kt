package com.maq.propertyapp.properties

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.maq.propertyapp.R
import com.maq.propertyapp.databinding.ListViewItemBinding

class PropertyAdapter(val propertyList: Property, val context: Context
                      ): RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
         PropertyViewHolder(
             DataBindingUtil.inflate(
                 LayoutInflater.from(parent.context),
                 R.layout.list_view_item,
                 parent,
                 false
             )
        )

    override fun getItemCount() = propertyList.data.listings.size



    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {

        holder.listViewItemBinding.property = propertyList.data.listings[position]
        val imageUrls = propertyList.data.listings[position].ImageUrls

        //setup imageslider to display multiple images in imageview
        holder.listViewItemBinding.viewpager.adapter =
            PropertyImageSliderAdapter(
                context,
                imageUrls
            )
        holder.listViewItemBinding.indicator.setViewPager(holder.listViewItemBinding.viewpager)


        // Pager listener over indicator
        holder.listViewItemBinding.indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })

        //setup click for recyclerview item to display Id
        holder.listViewItemBinding.root.setOnClickListener { view ->
            displayToast(context,"Id : "+ propertyList.data.listings[position].Id)
        }

        //setup click for details button in recycler view item
        holder.listViewItemBinding.detailsButton?.setOnClickListener{
            showDialog(context,propertyList.data.listings[position].Description,propertyList.data.listings[position].AuctionDate,
                propertyList.data.listings[position].DateFirstListed, propertyList.data.listings[position].DateUpdated)
        }

    }


    private fun showDialog(context: Context,title:String,auctionDate:String,dateListed:String,dateUpdated:String) {

        val dialog = Dialog(context)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.custom_layout)
        dialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        dialog .findViewById<TextView>(R.id.body).text = title

        val auctiontext = context.getString(R.string.auction_date) +" "+ auctionDate
        dialog .findViewById<TextView>(R.id.auctionDate).text = auctiontext
        val datelistedtext = context.getString(R.string.date_listed) +" "+ dateListed
        dialog .findViewById<TextView>(R.id.dateListed).text = datelistedtext
        val dateupdatedtext = context.getString(R.string.date_updated) +" "+ dateUpdated
        dialog .findViewById<TextView>(R.id.dateUpdated).text = dateupdatedtext

        val cancelButton = dialog .findViewById(R.id.cancelButton) as ImageButton
        cancelButton.setOnClickListener {
            dialog .dismiss()
        }
        dialog .show()

    }

    fun displayToast(context: Context, message:String){

        val layout =  LayoutInflater.from(context).inflate(R.layout.custom_toast,null)
        val myToast = Toast(context)
        myToast.setGravity(Gravity.TOP,0,200)
        myToast.view = layout
        val toast_text = layout.findViewById(R.id.custom_toast_message) as TextView
        toast_text.text = message

        myToast.show()
    }


   inner class PropertyViewHolder(val listViewItemBinding: ListViewItemBinding) : RecyclerView.ViewHolder(listViewItemBinding.root)


}

