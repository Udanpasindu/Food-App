package com.example.foodapp.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.sql.RowId

@Dao
interface MealDao {

    @Insert
    fun addMeal(meal: Meal)

    @Insert
    fun addAll(vararg meal: Meal)

    @Query("SELECT * FROM Meal")
    fun getAll():LiveData<List<Meal>>

    @Query("SELECT * FROM Meal WHERE LOWER(Meal) LIKE '%' || :searchInput || '%' OR LOWER(Ingredients) LIKE '%' || :searchInput || '%'")
    fun searchMeal(searchInput:String):LiveData<List<Meal>>

    @Query("SELECT * FROM Meal WHERE LOWER(Meal) LIKE '%' || :searchInput || '%'")
    fun getMealByName(searchInput:String):LiveData<List<Meal>>
}