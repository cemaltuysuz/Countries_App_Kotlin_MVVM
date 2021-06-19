package com.thic.countries.service

import com.thic.countries.model.Model
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val baseUrl = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(API::class.java)

    fun getData():Single<List<Model>>{
        return api.getCountries()
    }
}