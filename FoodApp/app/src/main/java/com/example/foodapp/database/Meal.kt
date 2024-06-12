package com.example.foodapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.foodapp.utils.StringArrayConverter

@Entity
@TypeConverters(StringArrayConverter::class)
data class Meal(
    @PrimaryKey
    val id:Int=0,
    val Meal:String,
    val DrinkAlternate:String?,
    val Category:String?,
    val Area:String?,
    val Instructions:String?,
    val MealThumb:String?,
    val Tags:String?,
    val YouTube:String?,
    val Ingredients:ArrayList<String?>,
    val Measure:ArrayList<String?>,
    val Source:String?,
    val ImageSource:String?,
    val CreativeCommonsConfirmed:String?,
    val dateModified:String?,
    var visibility:Boolean=false
)