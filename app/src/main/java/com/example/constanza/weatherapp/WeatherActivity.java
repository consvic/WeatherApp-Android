package com.example.constanza.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {

        NetworkServices client;
    private TextView cityTV;
    private TextView descTV;
    private TextView tempTV;
    private ImageView weatherIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        String nameCity = intent.getStringExtra("itemPos");
        client = new NetworkServices();
        weatherIV = (ImageView) findViewById(R.id.weatherIcon);
        cityTV = (TextView) findViewById(R.id.cityTV);
        descTV = (TextView) findViewById(R.id.descTV);
        tempTV = (TextView) findViewById(R.id.tempTV);
        cityTV.setText(nameCity);

        final String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q="+ nameCity +"&APPID=ce847673e9580213614d3d070c6b31d5";
        //String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&APPID=ce847673e9580213614d3d070c6b31d5";
        Log.d("URL",weatherURL);

        new AsyncTask<Void, Void, Void>() {
            String res = "";
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    res = client.requestWeather(weatherURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String weatherString = jsonObject.getString("weather");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));

                    String cityName = jsonObject.getString("main");
                    double tempDouble = Double.parseDouble(jsonObjectMain.getString("temp"));
                    int temp = (int) tempDouble - 273;
                    JSONArray weatherArray = new JSONArray(weatherString);
                    JSONObject weatherInfoObject = weatherArray.optJSONObject(0);

                    String desc = weatherInfoObject.getString("description");
                    String descriptionCode = weatherInfoObject.getString("id");
                    int descCode = Integer.parseInt(descriptionCode);

                    descTV.setText(desc);
                    tempTV.setText(temp + "°C");

                    if(descCode >=200 && descCode < 300) {
                        //thunderstorm
                        weatherIV.setImageResource(R.mipmap.ic_storm);
                    }
                    if(descCode >= 300 && descCode < 600) {
                        //rain

                        weatherIV.setImageResource(R.mipmap.ic_rain);
                    }
                    if(descCode >= 600 && descCode < 700) {
                        //snow
                        weatherIV.setImageResource(R.mipmap.ic_snow);
                    }
                    if(descCode >= 700 && descCode < 800) {
                        //cloudy
                        weatherIV.setImageResource(R.mipmap.ic_cloudy);
                    }
                    if(descCode == 800) {
                        //sunny
                        weatherIV.setImageResource(R.mipmap.ic_sun);
                    }
                    if(descCode > 800) {
                        //cloudy
                        weatherIV.setImageResource(R.mipmap.ic_cloudy);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }





}
