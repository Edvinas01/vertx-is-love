package com.edd.vertxtests;

import com.edd.vertxtests.car.Car;
import com.edd.vertxtests.car.Wheel;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FuturesNested {

    public static void main(String[] args) {
        Future<Wheel> frontMiddle = createWheel();
        Future<Wheel> backRight = createWheel();
        Future<Wheel> backLeft = createWheel();

        CompositeFuture
                .all(frontMiddle, backRight, backLeft)
                .setHandler(wheelResult -> {
                    if (wheelResult.succeeded()) {
                        List<Wheel> wheelList = wheelResult.result().list();

                        createCar(wheelList).setHandler(carResult -> {
                            if (carResult.succeeded()) {
                                System.out.println("Created a car: " + carResult.result());
                            } else {
                                System.out.println("Could not create a car");
                            }
                        });

                    } else {
                        System.out.println("Could not create wheels");
                    }
                });
    }

    private static Future<Wheel> createWheel() {
        return Future.future(result -> {
            int randomTorque = Math.abs((int) System.currentTimeMillis());
            Wheel wheel = new Wheel(randomTorque);
            result.complete(wheel);
        });
    }

    private static Future<Car> createCar(Collection<Wheel> wheels) {
        return Future.future(result -> {
            Car car = new Car(new ArrayList<>(wheels));
            result.complete(car);
        });
    }
}
