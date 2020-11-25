package com.app.calculator_betona.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.ceil

data class ViewModel(
    val volume: MutableLiveData<Double> = MutableLiveData(),
    val mass: MutableLiveData<Double> = MutableLiveData(),
    val concreteRatio: MutableLiveData<Double> = MutableLiveData(),
    val sandRatio: MutableLiveData<Double> = MutableLiveData(),
    val crushedStoneRatio: MutableLiveData<Double> = MutableLiveData(),
    val concretePrice: MutableLiveData<Double> = MutableLiveData(),
    val sandPrice: MutableLiveData<Double> = MutableLiveData(),
    val crushedStonePrice: MutableLiveData<Double> = MutableLiveData(),
    val price: MutableLiveData<Double> = MutableLiveData(),
    val city: MutableLiveData<String> = MutableLiveData()
) : ViewModel() {
    init {
        volume.value = 0.0
        mass.value = 0.0
        concreteRatio.value = 1.0
        sandRatio.value = 1.0
        crushedStoneRatio.value = 1.0
        concretePrice.value = 0.0
        sandPrice.value = 0.0
        crushedStonePrice.value = 0.0
        price.value = 0.0
        city.value = "Москве"
    }

    fun getVolume() = volume.value.toString()
    fun setVolume(string: String) = run { volume.value = string.ifEmpty { "0" }.toDouble() }

    fun getMass() = mass.value.toString()
    fun setMass(string: String) = run { mass.value = string.ifEmpty { "0" }.toDouble() }

    fun getConcreteRatio() = concreteRatio.value.toString()
    fun setConcreteRatio(string: String) =
        run { concreteRatio.value = string.ifEmpty { "0" }.toDouble() }

    fun getCrushedStoneRatio() = crushedStoneRatio.value.toString()
    fun setCrushedStoneRatio(string: String) =
        run { crushedStoneRatio.value = string.ifEmpty { "0" }.toDouble() }

    fun getSandRatio() = sandRatio.value.toString()
    fun setSandRatio(string: String) = run { sandRatio.value = string.ifEmpty { "0" }.toDouble() }

    fun getConcretePrice() = concretePrice.value.toString()
    fun setConcretePrice(string: String) =
        run { concretePrice.value = string.ifEmpty { "0" }.toDouble() }

    fun getSandPrice() = sandPrice.value.toString()
    fun setSandPrice(string: String) = run { sandPrice.value = string.ifEmpty { "0" }.toDouble() }

    fun getCrushedStonePrice() = crushedStonePrice.value.toString()
    fun setCrushedStonePrice(string: String) =
        run { crushedStonePrice.value = string.ifEmpty { "0" }.toDouble() }

    private fun getPart() =
        volume.value!! * 1000 / (concreteRatio.value!! + sandRatio.value!! + crushedStoneRatio.value!!)

    fun getConcreteMass() = concreteRatio.value!! * getPart()

    fun getPackages() = ceil(getConcreteMass() / mass.value!!)

    fun getSandMass() = sandRatio.value!! * getPart()

    fun getCrushedStoneMass() = crushedStoneRatio.value!! * getPart()

    fun getConcreteCost() = (concretePrice.value!! * getPackages()).run {
        if (isNaN()) {
            0.0
        } else {
            this
        }
    }

    fun getSandCost() = getSandMass() * sandPrice.value!! / 1000

    fun getCrushedStoneCost() = getCrushedStoneMass() * crushedStonePrice.value!! / 1000

    fun getTotal() = (getConcreteCost() + getCrushedStoneCost() + getSandCost()).run {
        if (isNaN()) {
            0.0
        } else {
            this
        }
    }

    fun getPricePerVolume() = (getTotal() / volume.value!!).run {
        if (isNaN()) {
            0.0
        } else {
            this
        }
    }

    fun getEconomy() = getTotal() - price.value!! * volume.value!!
}
