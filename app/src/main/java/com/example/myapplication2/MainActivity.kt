package com.example.myapplication2

import WeatherResponse
import WeatherService
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var weatherService: WeatherService
    private lateinit var locationEditText: EditText
    private lateinit var displayTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherService = retrofit.create(WeatherService::class.java)

        locationEditText = findViewById(R.id.editTextText)
        displayTextView = findViewById(R.id.textView)

        val fetchButton: Button = findViewById(R.id.button)
        fetchButton.setOnClickListener {
            val location = locationEditText.text.toString().trim()
            if (location.isNotEmpty()) {
                fetchWeather(location)
            }
        }
    }

    private fun fetchWeather(location: String) {
        val apiKey = "c1c8e4e9f1488f98fc841e60ddfe941e"
        val call = weatherService.getWeather(location, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        val temperature = it.main.temp - 273.15 // Convert to Celsius
                        displayTextView.text = "Location: ${it.name}\nTemperature: ${String.format("%.2f", temperature)}°C"
                    }
                } else {
                    displayTextView.text = "Failed to fetch weather data"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                displayTextView.text = "Failed to fetch weather data: ${t.message}"
            }
        })

    }
    val weatherIconMap = mapOf(
        "01d" to R.drawable.reshot_icon_sun_s9zw4t6ugq,
        "01n" to R.drawable.reshot_icon_moon_qhps8a7cj5,
        "02d" to R.drawable.reshot_icon_sun_and_cloud_65pmrjt84g,
        "02n" to R.drawable.reshot_icon_night_moon_q26ha4b35f,
        "03d" to R.drawable.reshot_icon_sun_and_cloud_uhyfetrwj2,
        "03n" to R.drawable.reshot_icon_cloud_moon_bmwvydzeua,
        "04d" to R.drawable.reshot_icon_cloud_9ckd27r8uz,
        "04n" to R.drawable.reshot_icon_cloud_9ckd27r8uz,
        "09d" to R.drawable.reshot_icon_rain_clouds_qtlw32d7fr,
        "09n" to R.drawable.reshot_icon_rain_clouds_qtlw32d7fr,
        "10d" to R.drawable.reshot_icon_rain_5yxumgh6zb,
        "10n" to R.drawable.reshot_icon_rain_5yxumgh6zb,
        "11d" to R.drawable.reshot_icon_electric_clouds_dkly6ef9h5,
        "11n" to R.drawable.reshot_icon_electric_clouds_dkly6ef9h5,
        "13d" to R.drawable.reshot_icon_snow_cloud_tpc7fb3gav,
        "13n" to R.drawable.reshot_icon_snow_cloud_tpc7fb3gav,
        //"50d" to R.drawable.ic_mist,
        //"50n" to R.drawable.ic_mist
    )

//    // Znajdujemy referencję do ImageView w układzie XML
//    val weatherIconImageView: ImageView = findViewById(R.id.imageView)
//
//    // Po otrzymaniu odpowiedzi z usługi pogodowej, ustawiamy obrazek na podstawie ikony pogody
//    val weatherResponse = response.body()
//    weatherResponse?.let {
//        val weatherIcon = it.weather.firstOrNull()?.icon
//        weatherIcon?.let { icon ->
//            val drawableRes = weatherIconMap[icon]
//            drawableRes?.let {
//                weatherIconImageView.setImageResource(it)
//            }
//        }
//    }
}





