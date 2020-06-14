package com.example.rmider.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.Serializable;

public class Account implements Serializable {
    private String phoneNumber;
    private String password;
    private String name;
    private String avatar;
    private String birthday;
    private boolean gender;
    private String position;
    private String cmnd;
    private Car     myCar;

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public Account() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Car getMyCar() {
        return myCar;
    }

    public void setMyCar(Car myCar) {
        this.myCar = myCar;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
