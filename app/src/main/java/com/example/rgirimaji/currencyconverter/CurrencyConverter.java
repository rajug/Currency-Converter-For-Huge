package com.example.rgirimaji.currencyconverter;

import android.app.Dialog;

import com.example.rgirimaji.currencyconverter.service.ResultsModel;
import com.example.rgirimaji.mvp.IPresenter;
import com.example.rgirimaji.mvp.IView;

/**
 * Created by rgirimaji on 12/2/16.
 */

public class CurrencyConverter
{
  public interface Presenter extends IPresenter
  {
    boolean validateInput();

    void onDataAvailable(ResultsModel model);

    void onDataError();

  }

  public interface View extends IView
  {
    void onCreate(EventHandler eventHandler, CurrencyConverterModel ccModel);

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
