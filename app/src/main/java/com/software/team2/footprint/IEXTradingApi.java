package com.software.team2.footprint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEXTradingApi {
   @GET("stock/market/news/last/10")
   Call<List<NewsMatches>> getNewsMatch();
}
