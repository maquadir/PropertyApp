# PropertyApp
This is an Android Application written in Kotlin to display a collection of properties loaded from a JSON url.

# Installation
Clone the repo and install the dependencies.
        
      git clone git@github.com:wolox/<reponame>.git
      
# Architecture and Design
The application follows an MVVM architecture as given below

<img width="449" alt="Screen Shot 2019-12-25 at 8 05 55 AM" src="https://user-images.githubusercontent.com/19331629/71425127-6ca3cc00-26ed-11ea-98b5-a344b54b7050.png">
      
# Setup
### Manifest File
- Since the app is going to fetch from json url .We have to add the following internet permissions to the manifest file.
    
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 
- The app supports orientation change and adapts to both portrait and landscape modes by mentioning screen orientation as 'sensor' which detects screen change and adapts its layout.

    android:screenOrientation="sensor"
    
### Material Styling
A progress bar is displayed during thee JSON operation.

### JSON Operation using Retrofit.Builder()
We have declared an API interface to deal with the JSON request using Retrofit

         return Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(PropertiesApi::class.java)
                
### Model
A Modelcontains all the data classes, database classes, API and repository.
A Property data class is created using JSON to Kotlin class plugin to map the JSON data to Kotlin. A Properties Api class to handle api requests and a repository takes care of how data will be fetched from the api.
              
              data class Property (
                     val ad_id : Int,
                     var data: data
                  )
                  
              val api = PropertiesApi()
              val repository = PropertiesRepository(api)

### View Model
We set up a view model factory which is responsible for creating view models

        factory = PropertyViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(PropertyViewModel::class.java)

### Coroutines
Coroutines are a great way to write asynchronous code that is perfectly readable and maintainable. We use it to perform a job of reading from the JSON url.

        fun<T: Any> ioThenMain(work: suspend (() -> T?), callback: ((T?)->Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async  rt@{
                return@rt work()
            }.await()
            callback(data)
        }

        job = Coroutines.ioThenMain(
                    { repository.getProperties() },
                    { _properties.value = it }
                )

### View
A Recycler View displays the data read from the JSON. We setup a recycler view adapter to take care of displaying the data on the view.
- The View model observes any data change and updates the adapter.

      viewModel.properties.observe(this, Observer { properties ->
            recyclerView.also {
                findViewById<ProgressBar>(R.id.loader).visibility = View.INVISIBLE
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = PropertyAdapter(properties,this
                )
            }
        })
        
- The recyclerview uses an image slider adapter to display multiple images in an imageview along with a page indictor.
        
        holder.listViewItemBinding.viewpager.adapter =
            PropertyImageSliderAdapter(
                context,
                imageUrls
            )
        holder.listViewItemBinding.indicator.setViewPager(holder.listViewItemBinding.viewpager)
        
- We use Glide to display profile image
      
      @BindingAdapter("image")
      fun loadImage(view: ImageView, url: String) {
          Glide.with(view)
              .load(url)
              .into(view)
      }



### Build Gradle
We declare the respective dependdencies to execute the above

    //Glide
    implementation 'com.github.bumptech.glide:glide:4.10.0'

    //recycler view and card view
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'com.android.support:cardview-v7:28.0.0'

    //Kotlin Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.1'
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.1"

    //Retrofit and GSON
    implementation 'com.squareup.retrofit2:retrofit:2.6.0'
    implementation 'com.squareup.retrofit2:converter-moshi:2.6.0'

    // ViewModel and LiveData
    implementation "androidx.lifecycle:lifecycle-extensions:2.1.0"

    //New Material Design
    implementation 'com.google.android.material:material:1.2.0-alpha03'

    //Kodein Dependency Injection
    implementation "org.kodein.di:kodein-di-generic-jvm:6.2.1"
    implementation "org.kodein.di:kodein-di-framework-android-x:6.2.1"

    //Viewpage indicators
    compile ('com.github.JakeWharton:ViewPagerIndicator:2.4.1')  {
        exclude group: 'com.android.support'
        exclude module: 'appcompat-v7'
        exclude module: 'support-v4'
    }

# Screenshots
<img width="350" alt="Screen Shot 2019-12-25 at 8 05 55 AM" src="https://user-images.githubusercontent.com/19331629/71426011-c90be900-26f7-11ea-985a-b9b1f5b37caa.png"> <img width="350" alt="Screen Shot 2019-12-25 at 8 05 55 AM" src="https://user-images.githubusercontent.com/19331629/71426028-feb0d200-26f7-11ea-981d-d7ba721be139.png">

