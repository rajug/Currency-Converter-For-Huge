package com.example.rgirimaji.currencyconverter;

import android.app.Dialog;
import android.databinding.ObservableField;
import android.support.annotation.StringRes;
import android.view.View;

import com.example.rgirimaji.currencyconverter.service.FixturesEndPoint;
import com.example.rgirimaji.currencyconverter.service.FixturesService;
import com.example.rgirimaji.currencyconverter.service.FixturesServiceImpl;
import com.example.rgirimaji.currencyconverter.service.ResultsModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by rgirimaji on 12/2/16.
 */

public class CurrencyConverterPresenter implements CurrencyConverter.Presenter, CurrencyConverter.EventHandler
{
  private final CurrencyConverter.View _view;
  private final CurrencyConverterModel _model;
  public CurrencyConverterPresenter(CurrencyConverter.View v, CurrencyConverterModel model)
  {
    _view = v;
    _model = model;
  }

  @Override
  public void onCreate()
  {
    _view.onCreate(this, _model);
  }

  @Override
  public void onResume()
  {
    _view.setCursorEnd();
  }

  @Override
  public void onPause()
  {

  }

  @Override
  public void onDataAvailable()
  {

  }

  @Override
  public void onDataError()
  {

  }

  private boolean validateInput()
  {
    return _model.usd.get() != null && _model.usd.get().trim().length() > 0;
  }

  @Override
  public void onConvertClicked(View view)
  {
    if (!validateInput())
    {
      _view.showValidationError();
    }
    else
    {
      _model.usd.set(String.valueOf(Integer.valueOf(_model.usd.get())));
      _view.hideKB();
      getRates(_view.showSpinner());
    }
  }

  private void getRates(final Dialog spinner)
  {
    FixturesService fixturesService = new FixturesServiceImpl();
    fixturesService.getRates(new FixturesService.FixturesServiceCallback()
    {
      @Override
      public void onSuccess(ResultsModel resultsModel)
      {
        spinner.dismiss();
        if (resultsModel != null && resultsModel.getRates() != null && resultsModel.getRates().size() > 0)
        {
          processResults(resultsModel);
        }
      }

      @Override
      public void onError()
      {
        spinner.dismiss();
        onError();
      }
    });
  }

  private void onError()
  {
    _view.showNetworkError();
  }

  private void processResults(ResultsModel resultsModel)
  {
    _model.lastUsd.set(Integer.valueOf(_model.usd.get()));
    _model.showResults.set(true);
    _model.lastDate.set(resultsModel.getDate() + " " + new SimpleDateFormat("hh:mm:ss a").format(Calendar.getInstance().getTime()));
    setRate(resultsModel, "GBP", _model.gbp, R.string.gbp_value);
    setRate(resultsModel, "JPY", _model.jpy, R.string.jpy_value);
    setRate(resultsModel, "BRL", _model.brl, R.string.brl_value);
  }

  private void setRate(ResultsModel resultsModel, String key, ObservableField<String> field, @StringRes int str)
  {
    if (resultsModel.getRates().containsKey(key))
    {
      Double exchangeRate = resultsModel.getRates().get(key);
      Double val = exchangeRate * _model.lastUsd.get();
      field.set(String.format(_view.getContext().getString(str), val, exchangeRate));
    }
  }
}
