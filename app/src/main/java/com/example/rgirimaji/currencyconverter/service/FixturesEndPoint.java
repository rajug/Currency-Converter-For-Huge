package com.example.rgirimaji.currencyconverter.service;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by rgirimaji on 12/3/16.
 */

public interface FixturesEndPoint
{
  @GET("/latest?base=USD")
  Call<ResultsModel> getRates();
}
