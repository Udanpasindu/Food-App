package com.example.foodapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.MealRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MealViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MealRepo
    val readAllData: LiveData<List<Meal>>
    private lateinit var getSearchedMeals:LiveData<List<Meal>>
    private var searchText:String=""
    private var uniqueMeals:ArrayList<Meal> = ArrayList()

    init {
        val mealDao = MealDatabase.getDatabase(application).mealDao()
        repository = MealRepo(mealDao)
        readAllData = repository.getAll()
    }

    fun addMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMeal(meal)
        }
    }

    fun addAll(vararg meal: Meal){
        checkAvailability(*meal)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAll(*meal)
        }
    }

    fun searchMeal(searchedText:String):LiveData<List<Meal>>{
        getSearchedMeals= MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            getSearchedMeals= repository.searchMeal(searchedText)
        }
        return getSearchedMeals
    }
    fun getAll():LiveData<List<Meal>>{
        return readAllData
    }

    private fun checkAvailability(vararg meals:Meal){
        println(readAllData.value)
    }


}