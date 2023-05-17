package com.andrey.traveltime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DateViewModel : ViewModel() {
    private val _departureDate = MutableLiveData<Long>()
    val departureDate: LiveData<Long> = _departureDate

    private val _returnDate = MutableLiveData<Long>()
    val returnDate: LiveData<Long> = _returnDate

    private val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val minDate = Calendar.getInstance().apply {
        add(Calendar.YEAR, 0) //Минимальная дата вылета, год
        set(Calendar.DAY_OF_MONTH, 0) // Сегодня (заказать билет на вчера нельзя)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }.timeInMillis

    val maxDate = Calendar.getInstance().apply {
        add(Calendar.YEAR, 1) // Максимальная дата - 1 год вперёд
    }.timeInMillis

    init {
        _departureDate.value = minDate
        _returnDate.value = minDate
    }

    fun setDepartureDate(year: Int, month: Int, dayOfMonth: Int) {
        val selectedDeparture = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        _departureDate.value = selectedDeparture.timeInMillis
    }

    fun setReturnDate(year: Int, month: Int, dayOfMonth: Int) {
        val selectedReturn = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        _returnDate.value = selectedReturn.timeInMillis
    }
}