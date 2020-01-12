package com.maq.propertyapp.properties

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maq.propertyapp.R
import com.maq.propertyapp.util.Coroutines
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PropertyViewModel(private val repository: PropertiesRepository) : ViewModel() {

    private lateinit var job: Job

    private val _properties = MutableLiveData<Property>()
    val properties : LiveData<Property>
                get() = _properties

    init{
        Log.i("View Model","View Model created")
    }

    //to keep track of lifetime of view model
    override fun onCleared() {
        super.onCleared()
        Log.i("View Model","View Model destroyed")
        if(::job.isInitialized) job.cancel()
    }

    //coroutines - get data from repository
    //fetching properties in iO thread and making changes to live data in main thread
     fun getProperties(){


            job =
                viewModelScope.launch {

                    Coroutines.ioThenMain(
                        { repository.getProperties() },
                        { _properties.value = it }
                    )
                }


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


    fun showDialog(context: Context,title:String,auctionDate:String,dateListed:String,dateUpdated:String) {

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



}