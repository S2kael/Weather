package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static String API_KEY = "4921d198b5e317a97f04bccfdafc1665";

    //EditText
    private EditText edtCityState;

    //Button
    private Button btnSearch;
    private Button btnForecast;

    //ImageView
    private ImageView imgWeather;
    private ImageView imgHumidity;
    private ImageView imgCloud;
    private ImageView imgWind;

    //TextView
    private TextView txtCity;
    private TextView txtCountry;
    private TextView txtTemperature;
    private TextView txtWeather;
    private TextView txtHumidity;
    private TextView txtCloud;
    private TextView txtWind;
    private TextView txtDateTime;

    //Coordinate
    private double lat;
    private double lon;

    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMapping();
        initImage();
        Intent intent =getIntent();
        String city = intent.getStringExtra("city");
        if (city != null) {
            if (city.isEmpty()){
                initData("HaNoi");
            }else {
                initData(city);
            }
        }else{
            initData("HaNoi");
        }
    }

    private void initData(String city) {
        Date currentTime = Calendar.getInstance().getTime();
        String dateTime =  DateFormat.format("E, yyyy-MM-dd HH:mm:ss", currentTime).toString();
        txtDateTime.setText(dateTime);

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlTemplate = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
        String url = String.format(urlTemplate, city, API_KEY);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                displayData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);
    }

    @SuppressLint("SetTextI18n")
    private void displayData(JSONObject response) {
        try {

            //Coordinate
            JSONObject coordinate = response.getJSONObject("coord");
            lon = coordinate.getDouble("lon");
            lat = coordinate.getDouble("lat");

            //City
            String city = response.getString("name");
            txtCity.setText(String.format(getResources().getString(R.string.city),city));
            cityName = city;

            //Country
            JSONObject sys = response.getJSONObject("sys");
            String country = sys.getString("country");
            txtCountry.setText(String.format(getResources().getString(R.string.country),country));

            //Temperature
            JSONObject main = response.getJSONObject("main");
            Double temp = main.getDouble("temp");
            txtTemperature.setText(String.format(getResources().getString(R.string.temperature),temp));


            //Weather Text
            JSONArray listWeather = response.getJSONArray("weather");
            JSONObject curWeather  = listWeather.getJSONObject(0);
            String displayWeather = curWeather.getString("main");
            txtWeather.setText(displayWeather);

            //Weather Icon
            String iconUrlTemplate = "http://openweathermap.org/img/wn/%s@4x.png";
            String icon = curWeather.getString("icon");
            String iconUrl = String.format(iconUrlTemplate,icon);
            Picasso.get().load(iconUrl).into(imgWeather);

            //Humidity
            Double humidity = main.getDouble("humidity");
            txtHumidity.setText(String.format(getResources().getString(R.string.humidity),humidity));

            //Cloud
            JSONObject cloud = response.getJSONObject("clouds");
            Double allCloud = cloud.getDouble("all");
            txtCloud.setText(String.format(getResources().getString(R.string.cloud),allCloud));

            //Wind
            JSONObject wind = response.getJSONObject("wind");
            Double windSpeed = wind.getDouble("speed");
            txtWind.setText(String.format(getResources().getString(R.string.wind),windSpeed));


        }catch (JSONException e) {
            Log.e("Display Data", e.getMessage() );
        }


    }

    private void initImage() {
        imgHumidity.setImageResource(R.drawable.humidity);
        imgCloud.setImageResource(R.drawable.cloud);
        imgWind.setImageResource(R.drawable.wind);
    }

    public void handleSearchClick(View view){
        String city = edtCityState.getText().toString();
        initData(city);
    }

    private void initMapping(){
        //EditText
        edtCityState = findViewById(R.id.edtCityState);

        //Button
        btnSearch = findViewById(R.id.btnSearch);
        btnForecast = findViewById(R.id.btnForecast);

        //ImageView
        imgWeather = findViewById(R.id.imgWeather);
        imgHumidity = findViewById(R.id.imgHumidity);
        imgCloud = findViewById(R.id.imgCloud);
        imgWind = findViewById(R.id.imgWind);

        //TextView
        txtCity = findViewById(R.id.txtCity);
        txtCountry = findViewById(R.id.txtCountry);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtWeather = findViewById(R.id.txtWeather);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtCloud = findViewById(R.id.txtCloud);
        txtWind = findViewById(R.id.txtWind);
        txtDateTime = findViewById(R.id.txtDateTime);
    }

    public void handleForecastClick(View view){
        Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
        intent.putExtra("lon", lon);
        intent.putExtra("lat", lat);
        intent.putExtra("cityName", cityName);
        startActivity(intent);
    }
}