package com.maq.propertyapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.maq.propertyapp.network.PropertiesApi
import com.maq.propertyapp.properties.PropertiesRepository
import com.maq.propertyapp.properties.PropertyAdapter
import com.maq.propertyapp.properties.PropertyViewModel
import com.maq.propertyapp.properties.PropertyViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toast.*


class MainActivity : AppCompatActivity() {

    private  lateinit var viewModel: PropertyViewModel
    private lateinit var factory: PropertyViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if phone is connected to internet
        if(isNetworkConnected() == false){
            displayToast("Connect your phone to internet and click refresh")
            return
        }

        setupUI()



    }

    private fun setupUI() {

        //display progress bar and toast
        findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        displayToast("Loading")

        //setup api and repository to read from JSON URL
        val api = PropertiesApi()
        val repository = PropertiesRepository(api)

        //setup view model
        factory =
            PropertyViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(PropertyViewModel::class.java)

        //get data from json reponse
        viewModel.getProperties()

        //setup recyclerview adapter with data
        viewModel.properties.observe(this, Observer { properties ->
            recyclerView.also {
                findViewById<ProgressBar>(R.id.loader).visibility = View.INVISIBLE
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = PropertyAdapter(
                    properties,
                    this
                )
            }
        })
    }

    //To create a menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    //To handle click events on menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_refresh -> {
            // do stuff
            Log.d("MainActivity","List is Refreshed ")

            //check if phone is connected to internet
            if(isNetworkConnected() == false){
                displayToast("Connect your phone to internet and click refresh")
            }else {
                //this method call fetches data from the json url and sets up recyclerview adapter
                displayToast("Loading")

                setupUI()
            }
            true

        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun isNetworkConnected(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

    fun displayToast(message:String){

        val layout = layoutInflater.inflate(R.layout.custom_toast,linearLayout)
        val myToast = Toast(applicationContext)
        myToast.setGravity(Gravity.TOP,0,200)
        myToast.view = layout
        val toastText = layout.findViewById(R.id.custom_toast_message) as TextView
        toastText.text = message

        myToast.show()
    }

}
