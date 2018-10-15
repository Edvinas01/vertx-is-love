package com.edd.vertxtests.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class Car {

    private final List<Wheel> wheels;

    @JsonCreator
    public Car(@JsonProperty("wheels") List<Wheel> wheels) {
        this.wheels = wheels;
    }

    public List<Wheel> getWheels() {
        return wheels;
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheels=" + wheels +
                '}';
    }
}
