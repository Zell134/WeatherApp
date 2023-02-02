package com.zell.weatherapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zell.weatherapp.utils.Properties;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class Weather {

    public interface Callback {
        void printWeather(WeatherResponse weather);
    }

    private final String city;
    private Callback callback;

    public Weather(String city) {
        this.city = city;
        getWeather();
    }

    public void registerCallBack(Callback callBack) {
        this.callback = callBack;
    }

    private void getWeather() {
        new AsyncQuery().execute();
    }

    class AsyncQuery extends AsyncTask<Void, Void, WeatherResponse> {

        @Override
        protected WeatherResponse doInBackground(Void... urls) {
            WeatherResponse weather;
            try {
                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder urlBuilder = HttpUrl.parse(Properties.URL).newBuilder()
                        .addQueryParameter("key", Properties.KEY)
                        .addQueryParameter("q", city);

                Request request = new Request.Builder()
                        .url(urlBuilder.build()).build();

                String response = client.newCall(request).execute().body().string();

                if (response == null) {
                    return null;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);
                weather = objectMapper.treeToValue(jsonNode.get("current"), WeatherResponse.class);

                if (weather != null) {
                    String iconRef = jsonNode.path("current").path("condition").get("icon").asText();
                    String cityFromResponse = jsonNode.path("location").get("name").asText();
                    weather.setCity(cityFromResponse);

                    if(iconRef !=null) {
                        iconRef = "https:" + iconRef;
                        request = new Request.Builder().url(iconRef).build();
                        try(InputStream stream = client.newCall(request).execute().body().byteStream()) {
                            Bitmap bitmap = BitmapFactory.decodeStream(stream);
                            weather.setIcon(bitmap);
                        }catch (Exception e){
                            weather.setIcon(null);
                        }
                    }

                    Log.d("Response", weather.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return weather;
        }

        @Override
        protected void onPostExecute(WeatherResponse weatherResponse) {
            callback.printWeather(weatherResponse);
            super.onPostExecute(weatherResponse);
        }
    }
}
