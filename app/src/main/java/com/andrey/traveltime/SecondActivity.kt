package com.andrey.traveltime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondActivity : AppCompatActivity() { //Второй экран
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second) //Установка макета для второго экрана

        val backButton = findViewById<Button>(R.id.backButton) //Для возвращения на первый экран
        backButton.setOnClickListener {
            finish()
        }
    }
}