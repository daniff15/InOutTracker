package ies.ua.inouttracker.api

import ies.ua.inouttracker.util.Constants.Companion.BASE_API_URL
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleAPI by lazy {
        retrofit.create(SimpleAPI::class.java)
    }
}