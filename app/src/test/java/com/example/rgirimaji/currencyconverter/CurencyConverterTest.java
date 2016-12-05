package com.example.rgirimaji.currencyconverter;

import android.app.Dialog;
import android.content.Context;
import android.databinding.repacked.google.common.collect.ImmutableMap;
import android.view.View;

import com.example.rgirimaji.currencyconverter.service.FixturesService;
import com.example.rgirimaji.currencyconverter.service.ResultsModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class CurencyConverterTest
{
  @Mock
  FixturesService fixturesService;
  @Mock
  ResultsModel resultsModel;
  @Mock
  CurrencyConverterView currencyConverterView;
  @Mock
  Context context;

  CurrencyConverterModel currencyConverterModel;
  CurrencyConverterPresenter currencyConverterPresenter;

  @Before
  public void setup()
  {
    fixturesService = mock(FixturesService.class);
    resultsModel = mock(ResultsModel.class);
    currencyConverterView = mock(CurrencyConverterView.class);
    currencyConverterModel = new CurrencyConverterModel();
    context = mock(Context.class);

    Dialog spinner = mock(Dialog.class);
    when(currencyConverterView.showSpinner()).thenReturn(spinner);

    when(currencyConverterView.getContext()).thenReturn(context);
    when(context.getString(any(Integer.class))).thenReturn("\\u00A5%.2f JPY (@%s)");

    currencyConverterPresenter = new CurrencyConverterPresenter(currencyConverterView, currencyConverterModel, fixturesService);
  }

  @Test
  public void testFixtureServiceCallbackEmptyResults()
  {
    prepareCallbackWithSuccess();
    currencyConverterPresenter.onConvertClicked(mock(View.class));
    verify(currencyConverterView, times(1)).showNetworkError();
  }



  @Test
  public void testFixtureServiceCallbackOnError()
  {
    prepareCallbackWithError();
    currencyConverterPresenter.onConvertClicked(mock(View.class));
    verify(currencyConverterView, times(1)).showNetworkError();
  }

  @Test
  public void testFixtureServiceCallbackNonemptyResults()
  {
    prepareCallbackWithSuccess();
    final Map<String, Double> rates = ImmutableMap.of(
        "BRL", 3.4682,
        "GBP", 0.79217,
        "JPY", 113.89,
         "EUR", 0.93967);

    when(resultsModel.getRates()).thenReturn(rates);
    currencyConverterPresenter.onConvertClicked(mock(View.class));
    verify(currencyConverterView, times(0)).showNetworkError();
    Assert.assertTrue(currencyConverterModel.showResults.get());
    Assert.assertTrue(currencyConverterModel.jpy.get().contains("113.89"));
  }


  @Test
  public void testCalculation()
  {
    prepareCallbackWithSuccess();
    final Map<String, Double> rates = ImmutableMap.of(
        "BRL", 3.4682,
        "GBP", 0.79217,
        "JPY", 113.89,
        "EUR", 0.93967);

    when(resultsModel.getRates()).thenReturn(rates);
    currencyConverterModel.usd.set("6");
    currencyConverterPresenter.onConvertClicked(mock(View.class));
    Assert.assertTrue(currencyConverterModel.jpy.get().contains("683.34"));
  }

  @Test
  public void testInputVaidation()
  {
    currencyConverterModel.usd.set("");
    currencyConverterPresenter.onConvertClicked(mock(View.class));
    verify(currencyConverterView, times(1)).showValidationError();
  }

  private void prepareCallbackWithSuccess()
  {
    doAnswer(new Answer()
    {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable
      {
        ((FixturesService.FixturesServiceCallback)invocation.getArguments()[0]).onSuccess(resultsModel);
        return null;
      }
    }).when(fixturesService).getRates(any(FixturesService.FixturesServiceCallback.class));

    currencyConverterModel.usd.set("1");
  }

  private void prepareCallbackWithError()
  {
    doAnswer(new Answer()
    {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable
      {
        ((FixturesService.FixturesServiceCallback)invocation.getArguments()[0]).onError();
        return null;
      }
    }).when(fixturesService).getRates(any(FixturesService.FixturesServiceCallback.class));

    currencyConverterModel.usd.set("1");
  }
}