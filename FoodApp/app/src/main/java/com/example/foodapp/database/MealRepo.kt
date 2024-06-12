package com.example.roomapp.data

import androidx.lifecycle.LiveData
import com.example.foodapp.database.Meal
import com.example.foodapp.database.MealDao

class MealRepo(private val mealDao: MealDao) {


    fun addMeal(meal: Meal){
        mealDao.addMeal(meal)
    }
    fun addAll(vararg meal: Meal){
        mealDao.addAll(*meal)
    }
    fun searchMeal(searchText:String):LiveData<List<Meal>>{
        val getSearchedMeals:LiveData<List<Meal>> =mealDao.searchMeal(searchText)
        println("Sent items count : "+ (getSearchedMeals.value?.size ?: -1))
        return getSearchedMeals
    }
    fun getAll():LiveData<List<Meal>>{
        val getAllMeals: LiveData<List<Meal>> = mealDao.getAll()
        return getAllMeals
    }

}