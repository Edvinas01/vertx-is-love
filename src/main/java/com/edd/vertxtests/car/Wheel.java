package com.edd.vertxtests.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Wheel {

    private final int torque;

    @JsonCreator
    public Wheel(@JsonProperty("torque") int torque) {
        this.torque = torque;
    }

    public int getTorque() {
        return torque;
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "torque=" + torque +
                '}';
    }
}
