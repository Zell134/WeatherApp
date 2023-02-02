package com.zell.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zell.weatherapp.activity.MainActivity;

public class Properties {
    public static final String URL = "http://api.weatherapi.com/v1/current.json";
    public static final String KEY = "b4b63ad6a28e42a0a57102438230102";
    public static final String SETTINGS = "settings";

    public static String getCity(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getString(MainActivity.CITY, null);
    }

    public static void saveCity(Context context, String value){
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        preferences.edit().putString(MainActivity.CITY, value).apply();
    }
}
