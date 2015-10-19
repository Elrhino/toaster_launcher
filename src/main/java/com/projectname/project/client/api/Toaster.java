package com.projectname.project.client.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Toaster {
    private String name;
    private String coordinates;
    private int power;

    @JsonCreator
    public Toaster(
            @JsonProperty("name") String name,
            @JsonProperty("coordinates") String coordinates,
            @JsonProperty("power") int power) {
        this.name = name;
        this.coordinates = coordinates;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
