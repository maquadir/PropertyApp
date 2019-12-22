package com.maq.propertyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.jetbrains.anko.toast


//import com.bumptech.glide.Glide


class Adapter(private val context: Context,
                    private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {

    private  lateinit var viewModel:MainViewModel


    //this variable is used to inflate the layout
    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //get the data in dataitem and inflate the item layout and store in rowView
        var dataitem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_view_item, parent, false)

        //calling viewModel
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //take the title, description and image from data item and pass it to the rowView to display in a row
        rowView.findViewById<TextView>(R.id.title).text = dataitem.get("Area")
        rowView.findViewById<TextView>(R.id.addr1).text = dataitem.get("Location_Address")
        rowView.findViewById<TextView>(R.id.addr2).text = dataitem.get("Location_Address2")
        rowView.findViewById<TextView>(R.id.addr3).text = dataitem.get("Location_State") +" "+ dataitem.get("Location_Suburb")
//        val url = URL(dataitem.get("ImageUrls0"))
//        val url = dataitem.get("ImageUrls0").toString().replace("http","https")
//        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//        rowView.findViewById<ImageView>(R.id.house_image).setImageBitmap(bmp)
        rowView.findViewById<TextView>(R.id.profile_name).text = dataitem.get("owner_name") +" "+ dataitem.get("owner_lastName")
        //change http to https 
        val url = dataitem.get("image_url_small").toString()
//        Log.i("image url", url)

        //use glide to lazy load images into imageview
//        try {
//            Glide.with(context)  //2
//                .load(url) //3
//                .centerCrop() //4
//                .placeholder(R.drawable.place_holder)
//                .into(rowView.findViewById<ImageView>(R.id.profile_image)) //8
//        }
//        catch(e:Exception){
//            Log.i("Image Error", e.toString())
//        }

        rowView.setOnClickListener {
            context.toast("Id : "+dataitem.get("Id").toString())

         }



        rowView.tag = position
        return rowView
    }

    //retrieve the item based on position
    override fun getItem(position: Int): Int { return position }

    override fun getItemId(position: Int): Long { return position.toLong() }

    //get the count of items
    override fun getCount(): Int { return dataList.size }
}