package com.example.weather.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.Unity.Weather;
import com.squareup.picasso.Picasso;

import java.security.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Weather> list;

    public WeatherAdapter(Context context, int layout, List<Weather> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView txtDateTime = view.findViewById(R.id.txtDateTime);
        TextView txtWeather = view.findViewById(R.id.txtWeather);
        TextView txtMaxTemperature = view.findViewById(R.id.txtMaxTemperature);
        TextView txtMinTemperature = view.findViewById(R.id.txtMinTemperature);
        ImageView imgWeather = view.findViewById(R.id.imgWeather);
        Weather weather = list.get(i);

        //Time
        Date date = new Date(weather.getDatetime()*1000);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, yyyy-MM-dd");
        //txtDateTime.setText(String.valueOf(weather.getDatetime()));
        txtDateTime.setText(format.format(date));

        //Weather
        txtWeather.setText(weather.getWeather());

        //Temperature
        txtMaxTemperature.setText(String.format(view.getResources().getString(R.string.temperature), weather.getMaxTemp()));
        txtMinTemperature.setText(String.format(view.getResources().getString(R.string.temperature), weather.getMinTemp()));

        //Icon
        String iconUrlTemplate = "http://openweathermap.org/img/wn/%s@2x.png";
        String icon = weather.getIcon();
        String iconUrl = String.format(iconUrlTemplate,icon);
        Picasso.get().load(iconUrl).into(imgWeather);

        return view;
    }
}
