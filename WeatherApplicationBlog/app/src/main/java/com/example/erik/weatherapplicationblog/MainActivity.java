package com.example.erik.weatherapplicationblog;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "0707ee16e1a9b5df5640707db0386721";
    private EditText townEditText;
    private EditText countryEditText;
    private TextView temperatureTextView;
    private ProgressBar progressBar;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViews();
        this.setButtonListener();
    }

    private void initViews(){
        this.townEditText = findViewById(R.id.et_town_name);
        this.countryEditText = findViewById(R.id.et_country_letters);
        this.temperatureTextView = findViewById(R.id.tv_temperature);
        this.progressBar = findViewById(R.id.progress_bar);
        this.searchButton = findViewById(R.id.btn_search);
    }

    private void setButtonListener(){
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String townName = townEditText.getText().toString().trim();
                final String country = countryEditText.getText().toString().trim();
                final String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,%s&APPID=%s&units=metric",
                        townName, country, API_KEY);

                new AsyncTaskManager().execute(url);
            }
        });
    }

    private class AsyncTaskManager extends AsyncTask<String, Long, Weather>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Weather weather) {
            temperatureTextView.setText(weather.getTemp().toString() + "°");
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Weather doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();
            Request request = new Request.Builder().url(strings[0]).build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                String json = response.body().string();
                WeatherMain weatherMain = gson.fromJson(json, WeatherMain.class);
                return weatherMain.getWeather();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}

