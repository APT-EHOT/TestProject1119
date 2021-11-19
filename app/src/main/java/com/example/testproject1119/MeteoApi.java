package com.example.testproject1119;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeteoApi {

    String HOST = "https://api.openweathermap.org/";
    String query = "Moscow";
    String appId = "9cc4fb816c8ec8e389ece7e71d8cef8b";

    @GET("data/2.5/forecast")
    Call<WeatherResponse> getMeteo(@Query("q") String query,
                                   @Query("appid") String appId);

}
