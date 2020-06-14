package com.example.rmider.model;

public class Fuel {
    private String  name;
    private int     picture;
    private int     price;
    private int     unitprice;
    private String editTextValue;

    public Fuel(String name, int picture, int price, int unitprice, String editTextValue) {
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.unitprice = unitprice;
        this.editTextValue = editTextValue;
    }

    public Fuel(String name, int picture, int price, int unitprice) {
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.unitprice = unitprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }

    public String getEditTextValue() {
        return editTextValue;
    }

    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }
}
