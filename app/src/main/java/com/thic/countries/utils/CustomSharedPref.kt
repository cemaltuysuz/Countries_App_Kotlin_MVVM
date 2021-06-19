package com.thic.countries.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPref {

    private val timeName = "time"

    companion object{
        private var sharedPreferences:SharedPreferences? = null

        @Volatile private var instance :CustomSharedPref? = null
        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPref = instance ?: synchronized(lock){
            instance ?: makeCustomSharedPref(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPref (context: Context) :CustomSharedPref{
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPref()
        }
    }

    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong(timeName,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(timeName,0)

}