package com.mh.quizapp.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    val baseURL = "https://192.168.31.209/quiz/"

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL).client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}