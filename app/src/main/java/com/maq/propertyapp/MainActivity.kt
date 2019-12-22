package com.maq.propertyapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import retrofit2.HttpException


class MainActivity : AppCompatActivity() {

    var actionbar_title: String = ""
//    var propertyList = ArrayList<HashMap<String, String>>()
    val propertyList = ArrayList<Property>()

    private  lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("View Model","Calling Main View Model")

        //calling viewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

//        var progressBar :ProgressBar = findViewById(R.id.loader)
//        progressBar.setVisibility(View.VISIBLE)

        //check if phone is connected to internet
        if(isNetworkConnected() == false){
            toast("Connect your phone to internet and click refresh")
            return
        }

        fetchFromJSON()


    }

    private fun fetchFromJSON() {

        val service = PropertiesApi.invoke()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getProperties()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.
                        toast("fetched from JSON")
                        response.body()?.let { showProperties(it) }
                        findViewById<ProgressBar>(R.id.loader).visibility = View.INVISIBLE
                    } else {
                        toast("Error: ${response.code()}")
                    }
                } catch (e: HttpException) {
                    toast("Exception ${e.message}")
                } catch (e: Throwable) {
                    toast("Ooops: Something else went wrong")
                }
            }
        }

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
                toast("Connect your phone to internet and click refresh")
            }else {
                //this method call fetches data from the json url
                //display a progress bar before background operation starts
                findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
                fetchFromJSON()
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

    private fun showProperties(properties: Property){

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PropertyAdapter(properties)

    }

}
