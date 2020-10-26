package com.example.weather.Unity;

public class Weather {
    private long datetime;
    private String weather;
    private String icon;
    private double minTemp;
    private double maxTemp;

    public Weather(long datetime, String weather, String icon, double minTemp, double maxTemp) {
        this.datetime = datetime;
        this.weather = weather;
        this.icon = icon;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
}
