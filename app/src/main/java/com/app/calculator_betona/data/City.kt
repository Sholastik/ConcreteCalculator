package com.app.calculator_betona.data

data class City(
    val cityKey: String,
    val cityName: String,
    val anotherCityName: String,
    val phone: String,
    val concretePrice: Double,
    val crushedStonePrice: Double,
    val sandPrice: Double,
    val price: Double
) {
    companion object {
        fun getDefaultCity() = City(
            "Moscow",
            "Москва",
            "Москве",
            "+74952950101",
            300.0,
            1500.0,
            500.0,
            2900.0
        )
    }
}

