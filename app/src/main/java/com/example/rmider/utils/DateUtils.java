package com.example.rmider.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    public static String getDate(long time){
        return new SimpleDateFormat("dd/MM/yyyy").format(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static long getTimeMilli(String date){
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
