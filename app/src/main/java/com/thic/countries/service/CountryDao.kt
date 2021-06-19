package com.thic.countries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thic.countries.model.Model

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll (vararg model:Model):List<Long>

    @Query("SELECT * FROM Model")
    suspend fun getAll():List<Model>

    @Query("SELECT * FROM Model WHERE uuid == :countryId")
    suspend fun getCountry(countryId:Int):Model

    @Query("DELETE FROM Model")
    suspend fun deleteAllCountries()
}