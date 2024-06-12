package com.example.foodapp.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.foodapp.R

class Loader(val myActivity:Activity) {
    private lateinit var isDialog:AlertDialog

    fun startLoader(){
        println("loadiing....")
        val inflater=myActivity.layoutInflater
        val dialogView=inflater.inflate(R.layout.loader,null)
        val builder=AlertDialog.Builder(myActivity)

        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog=builder.create()
        isDialog.show()
    }

    fun stopLoader(){
        println("stopped loading")
        isDialog.dismiss()
    }
}