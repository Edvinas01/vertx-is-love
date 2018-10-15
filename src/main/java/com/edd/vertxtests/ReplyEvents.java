package com.edd.vertxtests;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class ReplyEvents {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("addr", message -> {
            System.out.println(message.body());
            message.reply("pong");
        });

        eventBus.send("addr", "ping", message -> {
            if (message.succeeded()) {
                System.out.println(message.result().body());
            } else {
                System.out.println("Could not receive pong");
            }
        });
    }
}
