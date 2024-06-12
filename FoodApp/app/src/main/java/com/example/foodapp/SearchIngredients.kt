package com.example.foodapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.api.MealApiService
import com.example.foodapp.database.Meal
import com.example.foodapp.database.MealViewModel
import com.example.foodapp.utils.Loader
import com.example.foodapp.utils.MyAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchIngredients : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private lateinit var mealList: ArrayList<Meal>

    lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_ingredients)

        mealViewModel= ViewModelProvider(this).get(MealViewModel::class.java)

        val resultsTv:TextView=findViewById<TextView>(R.id.resultCount_tv)
        val searchbar:EditText=findViewById(R.id.searchIngre_et)
        val searchButton: Button =findViewById<Button>(R.id.btnsearch)
        val addtodbButton:Button=findViewById<Button>(R.id.btnsavetodb)
        val backBtn: ImageButton = findViewById(R.id.btnback)

        backBtn.setOnClickListener {
            finish()
        }

        val mealApiService:MealApiService= MealApiService()
        val loader=Loader(this)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        searchButton.setOnClickListener {
            imm.hideSoftInputFromWindow(resultsTv.getWindowToken(), 0)
            var searchedText=searchbar.text.toString().lowercase()
            if(isAlphabetic(searchedText)){
                runBlocking {
                    loader.startLoader()
                    launch {
                        mealApiService.searchAllMealsByIngredient(searchedText)
                        mealList=mealApiService.getResults()
                        loader.stopLoader()
                        resultsTv.text="${mealList.size} results found"
                        recyclerView.adapter=MyAdapter(mealList)
                    }
                }
            }else{println("No results!")}
        }

        addtodbButton.setOnClickListener {
            if (mealList.size>0){
                mealViewModel.addAll(*mealList.toTypedArray())
                Toast.makeText(this, "Data added to the database", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isAlphabetic(str: String): Boolean {
        return str.matches("[a-zA-Z]+".toRegex())
    }
}