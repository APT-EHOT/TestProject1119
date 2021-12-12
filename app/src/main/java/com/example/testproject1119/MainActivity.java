package com.example.testproject1119;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ProgressBar pb;
    public TextView tv;
    public List<WeatherItem> weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        pb = findViewById(R.id.progress);
        tv = findViewById(R.id.textView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MeteoApi.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Обязательно для взаимодействия Retrofit и RxJava
                .build();

        MeteoApi meteoApi = retrofit.create(MeteoApi.class);

        // Цепочка Rx вызовов
        meteoApi.getMeteo(MeteoApi.query, MeteoApi.appId)
                .subscribeOn(Schedulers.io()) // Подписка на источних данных в IO потоке
                .observeOn(AndroidSchedulers.mainThread()) // Переключаем поток на главный UI поток для изменения интерфейса
                .subscribe(new Observer<WeatherResponse>() { // Обработка событий подписки

                    // Срабатывает при начале подписки
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    // Срабатывает каждое событие
                    @Override
                    public void onNext(@NonNull WeatherResponse weatherResponse) {
                        weatherData = new ArrayList<>(weatherResponse.list);
                    }

                    // При ошибке
                    @Override
                    public void onError(@NonNull Throwable e) {
                        pb.setVisibility(View.INVISIBLE);
                        tv.setVisibility(View.VISIBLE);
                        Log.d("MyLog", "Ошибка");
                    }

                    // При завершении события
                    @Override
                    public void onComplete() {
                        MeteoAdapter meteoAdapter = new MeteoAdapter(weatherData);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(meteoAdapter);
                        pb.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } );


        System.out.println("test");
    }
}