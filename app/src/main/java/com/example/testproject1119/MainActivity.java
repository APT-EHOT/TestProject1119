package com.example.testproject1119;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public WeatherResponse wr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MeteoApi.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        MeteoApi meteoApi = retrofit.create(MeteoApi.class);

        Call<WeatherResponse> call = meteoApi.getMeteo(MeteoApi.query, MeteoApi.appId);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            MeteoAdapter meteoAdapter = new MeteoAdapter(Objects.requireNonNull(wr).list);
            recyclerView.setAdapter(meteoAdapter);
            recyclerView.refreshDrawableState();
        }, 1000);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                Log.d("MyLog", "Success");
                wr = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Log.e("MyLog", t.toString());
            }
        });

        System.out.println("test");
    }
}