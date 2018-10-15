package com.edd.vertxtests;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class VertxIntro {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.setPeriodic(1000, id -> System.out.println("Hello"));

        Vertx otherVertx = Vertx.vertx();
        otherVertx.setPeriodic(1000, id -> System.out.println("Hello from other"));

        Future<String> future = Future.future();
        otherVertx.setTimer(1000, handler -> future.complete("Hello"));

        future.setHandler(result -> {
            if (result.succeeded()) {
                System.out.println(result.result());
            }
        });
    }
}
