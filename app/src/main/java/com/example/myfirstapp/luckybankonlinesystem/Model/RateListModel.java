package com.example.myfirstapp.luckybankonlinesystem.Model;

import android.content.Context;
import android.util.JsonReader;

import com.example.myfirstapp.luckybankonlinesystem.R;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;

public class RateListModel {
    private HashMap<String, Double> currencyList;
    private String base;
    public RateListModel(String jsonStr, Context context) throws JSONException {
        JSONObject object = new JSONObject(jsonStr);
        JSONObject currencyObj = object.getJSONObject("rates");
        base = object.getString("base");
        String[] keys = context.getResources().getStringArray(R.array.currencies);
        currencyList = new HashMap<>();
        for (String key : keys) {
            currencyList.put(key, currencyObj.getDouble(key));
        }
    }

    public double getValue(String key) {
        return currencyList.get(key);
    }
}
