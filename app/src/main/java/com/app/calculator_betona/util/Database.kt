package com.app.calculator_betona.util

import com.app.calculator_betona.data.City
import com.crashlytics.android.Crashlytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private val database = FirebaseDatabase.getInstance().apply {
    setPersistenceEnabled(true)
}

private fun convertToCity(map: Map<String, Any>): City {
    val anotherCityName = map["anotherCityName"] as String
    val cementPrice = map["cement"] as Number
    val sandPrice = map["sand"] as Number
    val crushedStonePrice = map["crushedStone"] as Number
    val cityKey = map["cityENG"] as String
    val cityName = map["cityName"] as String
    val price = map["price"] as Number
    val phone = map["phone"] as String

    return City(
        cityKey, cityName, anotherCityName, phone,
        cementPrice.toDouble(), crushedStonePrice.toDouble(),
        sandPrice.toDouble(), price.toDouble()
    )
}

fun getCity(name: String, handler: DatabaseInterface) {
    val ref = database.reference
    ref.addListenerForSingleValueEvent(
        object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                snap.value?.let {
                    val list = it as ArrayList<Any?>
                    for (i in list) {
                        i?.let {
                            val value = i as Map<String, Any>
                            if (value["cityENG"] == name) {
                                handler.onDataReceived(convertToCity(value))
                                return
                            }
                        }
                    }
                    handler.onDataReceived(convertToCity(list[1] as Map<String, Any>))
                }
            }

            override fun onCancelled(snap: DatabaseError) {
                Crashlytics.log(snap.message)
                Crashlytics.logException(snap.toException())
            }
        }
    )
}

interface DatabaseInterface {
    fun onDataReceived(city: City)
}