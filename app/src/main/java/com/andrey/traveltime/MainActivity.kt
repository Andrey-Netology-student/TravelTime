package com.andrey.traveltime

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity" //Используется для логирования сообщений в консоли
private const val BASE_URL = "https://vmeste.wildberries.ru" //Базовый URL для выполнения HTTP-запросов к серверу

class MainActivity : AppCompatActivity() {
    private val dateViewModel: DateViewModel by viewModels() //Используется для управления данными, связанными с датами в приложении
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //Установка макета для главного экрана

        // Создаем экземпляр Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Создаем экземпляр API-сервиса
        val apiService = retrofit.create(WildberriesAPI::class.java)

        // Создаем экземпляр объекта для отправки запроса
        val requestData = RequestData("LED")

        // Отправляем запрос к API
        val call = apiService.getCheapestFlights(requestData)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val flights = response.body()?.data
                    flights?.forEach { flight ->
                        Log.d(TAG, "City: ${flight.city}")
                        Log.d(TAG, "Price: ${flight.price}")
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }
        })

        val like: ImageButton = findViewById(R.id.likeButton) //Кнопка лайк
        like.setOnClickListener {
            like.isSelected = !like.isSelected
        }

        val button = findViewById<Button>(R.id.switchingSecondActivity) //Переход на второй экран
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        /* когда пользователь выберет дату вылета, код автоматически обновит минимальную дату
для returnDate DatePickerDialog. Это должно предотвратить выбор даты возвращения раньше,
чем дата вылета. */
        val departureDateButton = findViewById<Button>(R.id.departureDateButton)
        departureDateButton.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    dateViewModel.setDepartureDate(year, month, dayOfMonth)
                },
                dateViewModel.year, dateViewModel.month, dateViewModel.day
            ).apply {
                datePicker.minDate = dateViewModel.minDate
                datePicker.maxDate = dateViewModel.maxDate
            }.show()
        }

        val returnDateButton = findViewById<Button>(R.id.returnDateButton)
        returnDateButton.setOnClickListener {
            DatePickerDialog( //Создается диалог выбора даты, который открывается при нажатии на кнопку
                this, //Указывается текущий контекст
                { _, year, month, dayOfMonth -> //начальные значения года, месяца и дня
                    dateViewModel.setReturnDate(year, month, dayOfMonth) //Устанавливает выбранную дату в качестве даты возврата
                },
                dateViewModel.year, dateViewModel.month, dateViewModel.day
            ).apply {
                datePicker.minDate = dateViewModel.departureDate.value ?: dateViewModel.minDate
                datePicker.maxDate = dateViewModel.maxDate
            }.show()
        }
    }
}