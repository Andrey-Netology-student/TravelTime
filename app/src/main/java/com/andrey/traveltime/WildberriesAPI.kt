package com.andrey.traveltime

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface WildberriesAPI {
    @Headers(
        "accept: application/json, text/plain, */*",
        "content-type: application/json"
    )
    @POST("e/GetCheap") //Аннотация указывает на метод запроса, который будет использоваться для отправки запроса на сервер.
    fun getCheapestFlights(@Body requestData: RequestData): Call<ApiResponse> //Тело запроса, которое будет отправлено на сервер в формате JSON.
}