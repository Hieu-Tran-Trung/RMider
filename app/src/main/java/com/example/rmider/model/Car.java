package com.example.rmider.model;

import java.io.Serializable;

public class Car implements Serializable {
    private String  modelName;
    private int     type;
    private String  color;
    private String  licensePlates;
    private String  technicalInformation;
    private String  numberFrames;
    private String  picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Car() {
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }

    public String getTechnicalInformation() {
        return technicalInformation;
    }

    public void setTechnicalInformation(String technicalInformation) {
        this.technicalInformation = technicalInformation;
    }

    public String getNumberFrames() {
        return numberFrames;
    }

    public void setNumberFrames(String numberFrames) {
        this.numberFrames = numberFrames;
    }
}
