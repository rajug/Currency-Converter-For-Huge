package com.example.rgirimaji.currencyconverter.service;

/**
 * Created by rgirimaji on 12/4/16.
 */

public interface FixturesService
{
  void getRates(FixturesServiceCallback callback);

  interface FixturesServiceCallback
  {
    void onSuccess(ResultsModel resultsModel);
    void onError();
  }
}
