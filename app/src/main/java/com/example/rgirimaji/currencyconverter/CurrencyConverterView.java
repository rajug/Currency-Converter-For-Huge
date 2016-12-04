package com.example.rgirimaji.currencyconverter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rgirimaji.currencyconverter.databinding.CurrencyConversionBinding;

import static android.databinding.DataBindingUtil.setContentView;


/**
 * Created by rgirimaji on 12/2/16.
 */

public class CurrencyConverterView implements CurrencyConverter.View
{
  final Activity _parent;
  CurrencyConversionBinding _viewBinding;
  public CurrencyConverterView(Activity parent)
  {
    _parent = parent;
  }

  @Override
  public void onCreate(CurrencyConverter.EventHandler eventHandler, CurrencyConverterModel model)
  {
    _viewBinding =
        setContentView(_parent, R.layout.currency_conversion);

    _viewBinding.setEventHandler(eventHandler);
    _viewBinding.setModel(model);
  }


  @Override
  public void hideKB()
  {
    InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    mgr.hideSoftInputFromWindow(_viewBinding.usd.getWindowToken(), 0);
  }

  @Override
  public void showValidationError()
  {
    Toast.makeText(getContext(), R.string.validation_error, Toast.LENGTH_LONG).show();
  }

  @Override
  public Dialog showSpinner()
  {
    final Dialog progress = ProgressDialog.show(getContext(), getContext().getString(R.string.progress_msg), "", true, false);
    progress.show();
    return progress;
  }

  @Override
  public void showNetworkError()
  {
    Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_LONG).show();
  }

  @Override
  public void setCursorEnd()
  {
    if (_viewBinding.usd.getText().length() > 0)
    {
      _viewBinding.usd.setSelection(_viewBinding.usd.getText().length());
    }
  }

  @Override
  public Context getContext()
  {
    return _parent;
  }
}
