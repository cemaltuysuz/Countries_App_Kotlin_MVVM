package com.thic.countries.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thic.countries.model.Model
import com.thic.countries.service.ApiService
import com.thic.countries.service.CountryDatabase
import com.thic.countries.utils.CustomSharedPref
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class CountriesViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = ApiService()
    private val disposable = io.reactivex.rxjava3.disposables.CompositeDisposable()
    private var customPref = CustomSharedPref(getApplication())
    private var refreshTime = 0.1 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Model>>()
    val countryLoading = MutableLiveData<Boolean>()
    val countryError = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPref.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime ){
            getDataFromSQLite()
        }else{
            getDataFromApi()
        }
    }

    private fun getDataFromApi(){
        countryLoading.value = true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : io.reactivex.rxjava3.observers.DisposableSingleObserver<List<Model>>(){
                    override fun onSuccess(t: List<Model>?) {
                        t?.let {
                            storeInSQLite(it)
                            customPref.saveTime(System.nanoTime())
                            Toast.makeText(getApplication(),"Countries from API",Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onError(e: Throwable?) {
                        countryLoading.value = false
                        countryError.value = true
                    }
                })
        )
    }
    private fun showCountries(list:List<Model>){
        countries.value = list
        countryError.value = false
        countryLoading.value = false
    }

    private  fun storeInSQLite(list:List<Model>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDatabase()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) // liste içindeki verileri tek tek hale getiriyor
            var i = 0
            while (i<list.size){
                list[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(list)
        }
    }

    private fun getDataFromSQLite(){
        launch {
            val countries = CountryDatabase(getApplication()).countryDatabase().getAll()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries from SQLite",Toast.LENGTH_SHORT).show()
        }
    }
}

