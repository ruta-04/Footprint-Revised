package com.software.team2.footprint;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlphavantageApi {
    @GET("/query")
    Call<Example> getStockInfo(
            @Query("function") String function,
            @Query("symbol") String symbol,
            @Query("apikey") String apikey);

}
