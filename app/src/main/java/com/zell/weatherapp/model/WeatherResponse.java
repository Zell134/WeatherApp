package com.zell.weatherapp.model;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @JsonProperty("temp_c")
    private double temp;

    private int humidity;

    @JsonProperty("pressure_mb")
    private double pressure;

    @JsonProperty("wind_kph")
    private double windSpeed;

    @JsonIgnore
    private Bitmap icon;

    @JsonIgnore
    private String city;

    public WeatherResponse(double temp, int humidity, double pressure, double windSpeed) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
    }

    public WeatherResponse() {
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "temp=" + temp +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", icon='" + icon + '\'' +
                '}';
    }
}
