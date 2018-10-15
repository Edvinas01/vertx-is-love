package com.edd.vertxtests;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class SimpleEvents {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("addr", message ->
                System.out.println("Received message: " + message.body())
        );

        eventBus.publish("addr", "Hello everyone");
        eventBus.publish("addr", new JsonObject().put("key", "value"));
    }
}
