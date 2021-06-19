package com.thic.countries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thic.countries.model.Model
import com.thic.countries.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Model>()

    fun getDataFromRoom (uuid:Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDatabase()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}