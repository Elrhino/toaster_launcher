package com.projectname.project.client.api;

public class Toaster {
    private String name;
    private String coordinates;
    private String power;

    private Toaster() {
        // For serialization
    }

    public Toaster(String name, String coordinates, String power) {
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

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
