package com.andrey.traveltime
// Создаем классы для отправки запроса и обработки ответа
data class ApiResponse(val data: List<Flight>)

data class Flight(val city: String, val airport: String, val price: Int)

data class RequestData(val startLocationCode: String)

data class CityInfo(val city: String, val price: Float)