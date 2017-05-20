package com.example.rakib.tourmate.WeatherUpdate;

/**
 * Created by Masum on 19-May-17.
 */

public class WeatherData {


    String dt;
    String min;
    String max;
    String humidity;
    String main;
    String description;
    String icon;


    public WeatherData() {
    }

    public WeatherData(String dt, String min, String max, String humidity, String main, String description, String icon) {
        this.dt = dt;
        this.min = min;
        this.max = max;
        this.humidity = humidity;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
