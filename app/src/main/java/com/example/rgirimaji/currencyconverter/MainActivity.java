package com.example.rgirimaji.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
  CurrencyConverterPresenter _ccPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setupMVP();
  }

  private void setupMVP()
  {
    CurrencyConverterView ccView = new CurrencyConverterView(this);
    CurrencyConverterModel ccModel = new CurrencyConverterModel();
    _ccPresenter = new CurrencyConverterPresenter(ccView, ccModel);
    _ccPresenter.onCreate();
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    _ccPresenter.onResume();
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    _ccPresenter.onPause();
  }
}
