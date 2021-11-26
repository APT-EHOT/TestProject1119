package com.example.testproject1119;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ProgressBar pb;
    public WeatherResponse wr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        pb = findViewById(R.id.progress);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MeteoApi.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        MeteoApi meteoApi = retrofit.create(MeteoApi.class);

        Call<WeatherResponse> call = meteoApi.getMeteo(MeteoApi.query, MeteoApi.appId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pb.postDelayed(() -> {
            MeteoAdapter meteoAdapter = new MeteoAdapter(wr.list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(meteoAdapter);
            pb.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }, 5000);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                wr = response.body();
                Log.d("MyLog", "Success");
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Log.e("MyLog", t.toString());
            }
        });

        System.out.println("test");
    }
}