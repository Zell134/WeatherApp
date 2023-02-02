package com.zell.weatherapp.presenter;

import android.graphics.Bitmap;

import com.zell.weatherapp.model.Weather;
import com.zell.weatherapp.model.WeatherResponse;

public class MainActivityPresenter implements Weather.Callback {

    public interface View {
        void updateCity(String value);
        void updateTemperature(String value);
        void updateHumidity(String value);
        void updatePressure(String value);
        void updateWindSpeed(String value);
        void updateWeatherIcon(Bitmap value);
        void throwError(String value);
        void hideProgressBar();
    }

    private final View view;

    public MainActivityPresenter(View view, String city) {
        this.view = view;
        Weather weather = new Weather(city);
        weather.registerCallBack(this);
    }

    public void updateWeather(String city){

        Weather weather = new Weather(city);
        weather.registerCallBack(this);
    }

    @Override
    public void printWeather(WeatherResponse weather) {
        if (weather == null) {
            view.throwError("Ошибка запроса данных");
            return;
        }
        view.updateCity(String.valueOf(weather.getCity()));
        view.updateTemperature("Температура воздуха: " + weather.getTemp() + " \u00B0");
        view.updateHumidity("Влажность: " + weather.getHumidity() + " %");
        view.updatePressure("Давление: " + String.format("%.2f",weather.getPressure() / 1.333) + " мм рт. ст.");
        view.updateWindSpeed("Скорость ветра: " + String.format("%.2f",weather.getWindSpeed() / 3.6) + " м/с");
        view.updateWeatherIcon(weather.getIcon());
        view.hideProgressBar();
    }
}
