package com.edd.vertxtests;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class Verticles {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new PrintingVerticle());
        vertx.deployVerticle(new PausingVerticle(), result -> {
            if (result.succeeded()) {
                System.out.println("Deployed");
            } else {
                System.out.println("Could not deploy");
            }
        });
    }

    private static final class PrintingVerticle extends AbstractVerticle {

        @Override
        public void start() {
            getVertx().setPeriodic(1000, id -> System.out.println("Hello"));
        }
    }

    private static final class PausingVerticle extends AbstractVerticle {

        @Override
        public void start(Future<Void> startFuture) throws Exception {
            Thread.sleep(1000);
            startFuture.complete();
        }
    }
}
