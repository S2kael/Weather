package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.Adapter.WeatherAdapter;
import com.example.weather.Unity.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity {
    private static String API_KEY = "4921d198b5e317a97f04bccfdafc1665";

    private TextView txtCityName;
    private ListView lvForecast;

    List<Weather> list;

    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);


        initMapping();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Double lon = intent.getDoubleExtra("lon", 0);
        Double lat = intent.getDoubleExtra("lat", 0);
        cityName = intent.getStringExtra("cityName");
        txtCityName.setText(cityName);

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlTemplate = "https://api.openweathermap.org/data/2.5/onecall?lat=%f&lon=%f&appid=%s&units=metric";
        String url = String.format(urlTemplate, lat, lon, API_KEY);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                displayData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForecastActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);
    }

    private void displayData(JSONObject response) {
        try {
            list = new ArrayList<>();
            JSONArray daily = response.getJSONArray("daily");
            for (int i = 0; i < daily.length(); i++) {
                JSONObject object = daily.getJSONObject(i);

                //Datetime
                long datetime = object.getLong("dt");

                //Temp
                JSONObject temp = object.getJSONObject("temp");
                double maxTemp = temp.getDouble("max");
                double minTemp = temp.getDouble("min");

                //Weather
                JSONArray listWeather = object.getJSONArray("weather");
                JSONObject curWeather = listWeather.getJSONObject(0);
                String displayWeather = curWeather.getString("description");

                //Icon
                String icon = curWeather.getString("icon");

                Weather weather = new Weather(datetime, displayWeather, icon, minTemp, maxTemp);
                list.add(weather);
            }

            WeatherAdapter adapter = new WeatherAdapter(ForecastActivity.this, R.layout.city, list);
            lvForecast.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e("displayData: ", e.getMessage());
        }
    }

    private void initMapping() {
        txtCityName = findViewById(R.id.txtCityName);
        lvForecast = findViewById(R.id.lvForecast);
    }

    public void handleBackClick(View view){
        finish();
    }
}