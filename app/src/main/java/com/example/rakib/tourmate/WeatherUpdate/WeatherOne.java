package com.example.rakib.tourmate.WeatherUpdate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rakib.tourmate.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherOne extends AppCompatActivity {

    String data;
    public static List<WeatherData> listData;
    ListView weatherListView;

    ProgressDialog pDialog;

    DBHelper databaseHelper;

    FirebaseAuth firebaseAuth;

    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_one);
        setTitle("Weather Update");

        firebaseAuth=FirebaseAuth.getInstance();
        databaseHelper = new DBHelper(this);

        //new GetDataAsyncTask().execute();

        weatherListView = (ListView) findViewById(R.id.weather_list);

        listData = databaseHelper.getAllUser();

        // Shared Preference
        SharedPreferences pref = getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE);

        pref.edit().putString("User", "Masum"); //TO put value.
        pref.edit().putString("Pass", "423"); //To put value.

        String user = pref.getString("User",""); //To get value.

        if (listData.size() < 1) {
            new GetDataAsyncTask().execute();
        } else {
            CustomAdapter adapter = new CustomAdapter(WeatherOne.this, listData);
            weatherListView.setAdapter(adapter);
        }

//        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Toast.makeText(Weather.this,"Clicked"+ position,Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(WeatherOne.this, DetailsActivityOne.class);
//                i.putExtra("pos", position);
//                startActivity(i);
//
//            }
//        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(WeatherOne.this);
            pDialog.setTitle("Please Wait ...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            data = getDataFromUrl("http://api.openweathermap.org/data/2.5/forecast/daily?APPID=105f1c46f067ef972bfc9c3b3319ea81&q=Dhaka&mode=json&units=metric&cnt=7");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            parseData(data);
            CustomAdapter cAdapter = new CustomAdapter(WeatherOne.this, listData);
            weatherListView.setAdapter(cAdapter);
            pDialog.dismiss();
        }
    }

    void parseData(String jsonData) {
        try {
            listData = new ArrayList<>();
            JSONObject object = new JSONObject(jsonData);


            JSONArray jsonArray = object.getJSONArray("list");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject arrayItem = jsonArray.getJSONObject(i);

                String dt = arrayItem.getString("dt");
                String humidity = arrayItem.getString("humidity");


                String tempData = arrayItem.getString("temp");
                JSONObject tempObj = new JSONObject(tempData);
                String maxTemp = tempObj.getString("max");
                String minTemp = tempObj.getString("min");

                JSONArray tempArray = arrayItem.getJSONArray("weather");
                JSONObject weatherObj = tempArray.getJSONObject(0);
                String main = weatherObj.getString("main");
                String description = weatherObj.getString("description");
                String icon = weatherObj.getString("icon");

                WeatherData weatherData = new WeatherData(dt, minTemp, maxTemp, humidity, main, description, icon);
                listData.add(weatherData);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String getDataFromUrl(String url) {

        String line = "";
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL urls = new URL(url);
            connection = (HttpURLConnection) urls.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(stream);
            reader = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line);

            }
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return line;
    }
}
