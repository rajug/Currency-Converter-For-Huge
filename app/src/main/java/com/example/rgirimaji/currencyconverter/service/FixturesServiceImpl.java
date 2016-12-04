package com.example.rgirimaji.currencyconverter.service;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by rgirimaji on 12/4/16.
 */

public class FixturesServiceImpl implements FixturesService
{
  private static final String BASE_URL = "http://api.fixer.io";

  public void getRates(final FixturesService.FixturesServiceCallback callback)
  {
    Retrofit retrofit = new retrofit.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    FixturesEndPoint fixturesEndPoint = retrofit.create(FixturesEndPoint.class);

    final Call<ResultsModel> call = fixturesEndPoint.getRates();
    //asynchronous call
    call.enqueue(new Callback<ResultsModel>()
    {
      @Override
      public void onResponse(Response<ResultsModel> response, retrofit.Retrofit retrofit)
      {
        callback.onSuccess(response.body());
      }

      @Override
      public void onFailure(Throwable t)
      {
        callback.onError();
      }
    });
  }
}
