package com.edd.vertxtests;

import com.edd.vertxtests.car.Car;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;

public class Controllers {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        Map<Long, Car> cars = new HashMap<>();
        router.get("/cars").handler(ctx -> ctx
                .response()
                .putHeader("content-type", "application/json")
                .end(Json.encodeToBuffer(cars))
        );

        router.get("/cars/:id").handler(ctx -> {
            Long id = Long.valueOf(ctx.pathParam("id"));

            Car car = cars.get(id);
            if (car == null) {
                JsonObject error = new JsonObject()
                        .put("error", "Car with id: " + id + " not found");

                ctx.response()
                        .setStatusCode(404)
                        .putHeader("content-type", "application/json")
                        .end(error.toBuffer());
            } else {
                ctx.response()
                        .putHeader("content-type", "application/json")
                        .end(Json.encodeToBuffer(car));
            }
        });

        router.post("/cars").handler(ctx -> {
            Car car = Json.decodeValue(ctx.getBody(), Car.class);
            cars.put(System.nanoTime(), car);
            ctx.response()
                    .putHeader("content-type", "application/json")
                    .end(Json.encodeToBuffer(car));
        });

        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        System.out.println("Started server on port 8080");
                    } else {
                        System.out.println("Could not start server");
                    }
                });
    }
}
