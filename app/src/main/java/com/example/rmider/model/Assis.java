package com.example.rmider.model;

public class Assis {
    private String  name;
    private int     picture;
    private String sdt;

    public Assis(String name, int picture, String sdt) {
        this.name = name;
        this.picture = picture;
        this.sdt = sdt;
    }

    public Assis() {
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
