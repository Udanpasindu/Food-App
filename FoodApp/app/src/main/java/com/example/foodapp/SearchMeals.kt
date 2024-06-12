package com.example.foodapp

import MyImageCardAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.database.Meal
import com.example.foodapp.database.MealViewModel
import com.example.foodapp.utils.Loader
import com.example.foodapp.utils.MyAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchMeals : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mealList: ArrayList<Meal>
    private lateinit var results: ArrayList<Meal>

    lateinit var mealViewModel:MealViewModel
    lateinit var resultsTv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meals)

        resultsTv=findViewById<TextView>(R.id.resultCount_tv)
        val searchButton: Button =findViewById<Button>(R.id.btnsearchDB)
        val searchBarET:EditText=findViewById(R.id.searchDB_et)

        val loader= Loader(this)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        mealList= ArrayList()
        results = ArrayList()

        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        mealViewModel= ViewModelProvider(this).get(MealViewModel::class.java)

        val backBtn: ImageButton = findViewById(R.id.btnback)

        backBtn.setOnClickListener {
            finish()
        }

        searchButton.setOnClickListener {
            imm.hideSoftInputFromWindow(resultsTv.getWindowToken(), 0)
            var searchedText=searchBarET.text.toString().lowercase()
            if(isAlphabetic(searchedText)){
                searchMeal(searchedText)
            }else{println("No results!")}
        }
    }
    private fun isAlphabetic(str: String): Boolean {
        return str.matches("[a-zA-Z]+".toRegex())
    }

    private fun searchMeal(searchText:String){
        try{
            results.clear()
            mealViewModel.getAll().observeForever { newList ->
                mealList = newList.toMutableList().toCollection(ArrayList())
                for (i in 0..mealList.size-1){
                    if (mealList.get(i).Meal.contains(searchText) || searchInIngredients(searchText,mealList.get(i))){
                        results.add(mealList.get(i))
                        continue
                    }else continue
                }
                resultsTv.text="${results.size} results found"
                recyclerView.adapter= MyImageCardAdapter(results)
            }
        }catch (e:Exception){
            print("Error!")
        }
    }

    private fun searchInIngredients(text:String,meal:Meal):Boolean{
        for (i in 0..meal.Ingredients.size-1){
            if (meal.Ingredients.get(i)?.contains(text) == true){
                return true
            }else continue
        }
        return false
    }
}