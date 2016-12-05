package com.example.rgirimaji.currencyconverter;

import android.databinding.ObservableField;

import com.example.rgirimaji.mvp.IModel;

/**
 * Created by rgirimaji on 12/2/16.
 */

public class CurrencyConverterModel implements IModel
{
  public final ObservableField<String> usd = new ObservableField<>("1");
  public final ObservableField<Long> lastUsd = new ObservableField<>();
  public final ObservableField<String> gbp = new ObservableField<>();
  public final ObservableField<String> jpy = new ObservableField<>();
  public final ObservableField<String> brl = new ObservableField<>();
  public final ObservableField<String> lastDate = new ObservableField<>();

  public final ObservableField<Boolean> showResults = new ObservableField<>();

}
