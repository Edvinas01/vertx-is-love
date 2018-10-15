package com.edd.vertxtests;

import com.edd.vertxtests.car.Car;
import com.edd.vertxtests.car.Wheel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;

import java.util.ArrayList;
import java.util.Collection;

public class VerticlesEventBus {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new WheelVerticle());
        vertx.deployVerticle(new CarVerticle());
    }

    private static final class WheelVerticle extends AbstractVerticle {

        @Override
        public void start() {
            EventBus eventBus = getVertx().eventBus();

            eventBus.consumer("wheels", message -> {
                Wheel wheel = new Wheel(123);
                message.reply(Json.encode(wheel));
            });
        }
    }

    private static final class CarVerticle extends AbstractVerticle {

        @Override
        public void start() {
            Future<Wheel> frontMiddle = createWheel();
            Future<Wheel> backRight = createWheel();
            Future<Wheel> backLeft = createWheel();

            CompositeFuture
                    .all(frontMiddle, backRight, backLeft)
                    .compose(composite -> createCar(composite.list()))
                    .setHandler(result -> {
                        if (result.succeeded()) {
                            System.out.println("Created a car: " + result.result());
                        } else {
                            System.out.println("Could not create a car");
                        }
                    });
        }

        private Future<Wheel> createWheel() {
            EventBus eventBus = getVertx().eventBus();

            Future<Wheel> result = Future.future();


            eventBus.send("wheels", null, message -> {
                if (message.succeeded()) {
                    String json = (String) message.result().body();
                    result.complete(Json.decodeValue(json, Wheel.class));
                } else {
                    result.fail(message.cause());
                }
            });

            return result;
        }

        private static Future<Car> createCar(Collection<Wheel> wheels) {
            return Future.future(result -> {
                Car car = new Car(new ArrayList<>(wheels));
                result.complete(car);
            });
        }
    }
}
