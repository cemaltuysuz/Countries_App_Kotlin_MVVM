package com.thic.countries.service

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.thic.countries.model.Model

@Database(entities = [Model::class],version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDatabase() : CountryDao

    companion object{

        @Volatile private var instance: CountryDatabase? = null

        private val lock = Any()

        operator fun invoke(context:Context) = instance ?: synchronized(lock){

            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context:Context) =
            Room.databaseBuilder(context,CountryDatabase::class.java,"Countries").build()
    }


}