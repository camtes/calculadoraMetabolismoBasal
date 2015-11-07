package com.ccamposfuentes.calculadorametabolismobasal;

import java.util.Date;

/**
 * Created by ccamposfuentes on 7/11/15.
 */
public class BMR {
    double mbr, calories, lostCalories, gainCalories;
    String date;

    public BMR(String date, double mbr, double calories, double lostCalories, double gainCalories) {
        this.mbr = mbr;
        this.date = date;
        this.calories = calories;
        this.lostCalories = lostCalories;
        this.gainCalories = gainCalories;
    }

    public double getBMR() {
        return mbr;
    }

    public String getDate() {
        return date;
    }

    public double getCalories() {
        return calories;
    }

    public double getLostCalories() {
        return lostCalories;
    }

    public double getGainCalories() {
        return gainCalories;
    }
}
