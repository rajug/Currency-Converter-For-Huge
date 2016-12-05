package com.example.rgirimaji.currencyconverter;

import android.content.res.AssetManager;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rgirimaji on 12/4/16.
 */

public class AppAdapters
{
  private static final Map<String, Typeface> sCachedTypeFaces = new HashMap<>();

  @BindingAdapter({"font"})
  public static void setFont(TextView textView, String fontName)
  {
    if (fontName == null || fontName.length() == 0)
    {
      return;
    }
    Typeface typeface = sCachedTypeFaces.get(fontName);
    if (typeface == null)
    {
      final AssetManager assetManager = textView.getContext().getAssets();
      String path = "fonts/" + fontName;
      try
      {
        typeface = Typeface.createFromAsset(assetManager, path);
        sCachedTypeFaces.put(fontName, typeface);
      } catch (Throwable t)
      {
        return;
      }
    }
    if (typeface != null)
    {
      textView.setTypeface(typeface);
    }
  }
}
