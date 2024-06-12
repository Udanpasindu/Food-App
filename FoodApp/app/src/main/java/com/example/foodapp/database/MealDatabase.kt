package com.example.foodapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Meal::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao():MealDao

    companion object {
        @Volatile//--available for all other threads
        private var INSTANCE: MealDatabase? = null

        fun getDatabase(context: Context): MealDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "MealDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}