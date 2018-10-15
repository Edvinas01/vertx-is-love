package com.edd.vertxtests;

import com.edd.vertxtests.car.Wheel;
import io.vertx.core.Future;

public class FuturesSimple {

    public static void main(String[] args) {
        success();
        failed();
    }

    private static void success() {
        Future<Wheel> success = Future.succeededFuture(new Wheel(123));

        success.setHandler(result -> {
            if (result.succeeded()) {
                System.out.println(result.result());
            } else {
                System.out.println("Could not create a wheel");
            }
        });
    }

    private static void failed() {
        Future<Wheel> failed = Future.failedFuture(new RuntimeException("Failed!"));

        failed.setHandler(result -> {
            if (result.succeeded()) {
                System.out.println(result.result());
            } else {
                System.out.println("Could not create a wheel");
            }
        });
    }
}
