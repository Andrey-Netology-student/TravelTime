package com.andrey.traveltime

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    /* Метод getClient(baseUrl: String): WildberriesAPI создает или возвращает существующий
    экземпляр класса Retrofit с базовым URL baseUrl и конвертером GsonConverterFactory. */
    fun getClient(baseUrl: String): WildberriesAPI {
        if (retrofit == null) {
            retrofit = Retrofit.Builder() //Создается новый экземпляр класса Retrofit.Builder()
                .baseUrl(baseUrl) //Устанавливает базовый URL для всех запросов, которые будут выполняться с помощью этого экземпляра класса Retrofit
                .addConverterFactory(GsonConverterFactory.create()) //устанавливает конвертер для преобразования данных, полученных с сервера, в объекты Kotlin
                .build()
        }
        return retrofit!!.create(WildberriesAPI::class.java) //Создает и возвращает экземпляр класса WildberriesAPI, который используется для выполнения HTTP-запросов к серверу
    }
}