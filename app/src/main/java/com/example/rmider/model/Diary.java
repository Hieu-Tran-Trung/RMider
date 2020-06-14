package com.example.rmider.model;

import java.io.Serializable;

public class Diary implements Serializable {
    private String key;
    private String date;
    private String start;
    private String end;

    public Diary() {
    }

    public Diary(String key, String date, String start, String end) {
        this.key = key;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
