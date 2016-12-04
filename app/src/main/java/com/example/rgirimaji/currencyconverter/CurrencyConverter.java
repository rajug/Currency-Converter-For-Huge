package com.example.rgirimaji.currencyconverter;

import android.app.Dialog;

import com.example.rgirimaji.mvp.BasePresenter;
import com.example.rgirimaji.mvp.BaseView;

/**
 * Created by rgirimaji on 12/2/16.
 */

public class CurrencyConverter
{
  public interface Presenter extends BasePresenter
  {
    void onDataAvailable();

    void onDataError();

  }

  public interface View extends BaseView
  {
    void onCreate(EventHandler eventHandler, CurrencyConverterModel ccModel);

    void showData();

    void hideKB();

    void showValidationError();

    Dialog showSpinner();

    void showNetworkError();

    void setCursorEnd();
  }

  public interface EventHandler
  {
    void onConvertClicked(android.view.View view);
  }

}
