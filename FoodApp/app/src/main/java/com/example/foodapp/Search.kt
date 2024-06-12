package com.example.foodapp

import MyImageCardAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
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

class Search : AppCompatActivity() {

    lateinit var searchBar:EditText
    lateinit var searchedText:String

    private lateinit var recyclerView: RecyclerView
    private lateinit var mealList: ArrayList<Meal>
    lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val mealApiService: MealApiService = MealApiService()
        val loader= Loader(this)
        val resultsTv: TextView =findViewById<TextView>(R.id.resultCount_tv)

        val backBtn: ImageButton = findViewById(R.id.btnback)

        backBtn.setOnClickListener {
            finish()
        }

        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        mealViewModel= ViewModelProvider(this).get(MealViewModel::class.java)

        searchBar=findViewById(R.id.searchIngre_et)

        searchBar.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                searchedText= searchBar.text.toString().lowercase()
                if(isAlphabetic(searchedText)){
                    runBlocking {
                        loader.startLoader()
                        launch {
                            mealApiService.searchAllMealsByName(searchedText)
                            mealList=mealApiService.getResults()
                            loader.stopLoader()
                            resultsTv.text="${mealList.size} results found"
                            recyclerView.adapter= MyImageCardAdapter(mealList)
                        }
                    }
                }else{println("No results!")}
            }

        })
    }
    private fun isAlphabetic(str: String): Boolean {
        return str.matches("[a-zA-Z]+".toRegex())
    }
}