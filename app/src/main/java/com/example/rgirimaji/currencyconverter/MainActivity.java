package com.example.rgirimaji.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rgirimaji.currencyconverter.service.FixturesService;
import com.example.rgirimaji.currencyconverter.service.FixturesServiceImpl;

public class MainActivity extends AppCompatActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setupMVP();
  }

  private void setupMVP()
  {
    FixturesService fixturesService = new FixturesServiceImpl();

    CurrencyConverterView ccView = new CurrencyConverterView(this);
    CurrencyConverterModel ccModel = new CurrencyConverterModel();
    CurrencyConverterPresenter ccPresenter = new CurrencyConverterPresenter(ccView, ccModel, fixturesService);
    ccPresenter.onCreate();
  }
}
