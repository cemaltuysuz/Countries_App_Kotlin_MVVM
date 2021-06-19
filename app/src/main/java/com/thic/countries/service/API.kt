package com.thic.countries.service

import com.thic.countries.model.Model
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Model>>
}