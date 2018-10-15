package com.edd.vertxtests

import com.edd.vertxtests.car.Car
import com.edd.vertxtests.car.Wheel
import io.vertx.core.Future
import io.vertx.kotlin.coroutines.await
import kotlinx.coroutines.experimental.runBlocking
import java.util.ArrayList

fun main(args: Array<String>) {
    runBlocking {
        try {
            val frontMiddle = createWheel().await()
            val backRight = createWheel().await()
            val backLeft = createWheel().await()

            val car = createCar(listOf(frontMiddle, backRight, backLeft)).await()

            println("Created a car: $car")
        } catch (e: Exception) {
            println("Could not create car")
        }
    }
}

fun createWheel(): Future<Wheel> = Future.future { result ->
    val randomTorque = Math.abs(System.currentTimeMillis().toInt())
    val wheel = Wheel(randomTorque)
    result.complete(wheel)
}

fun createCar(wheels: Collection<Wheel>): Future<Car> = Future.future { result ->
    val car = Car(ArrayList(wheels))
    result.complete(car)
}
