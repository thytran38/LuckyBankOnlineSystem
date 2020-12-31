package com.example.myfirstapp.luckybankonlinesystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfirstapp.luckybankonlinesystem.Adapter.RateAdapter;
import com.example.myfirstapp.luckybankonlinesystem.Model.RateListModel;
import com.google.api.Logging;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

public class CurrencyConverterActivity extends AppCompatActivity {
    EditText FirstCurrency, SecondCurrency;
    Button sConvert, sRefresh;
    //
    private RecyclerView ListRate;
    private RateAdapter rateAdapter;
    private ArrayList<RateListModel> rateListModel;
    private RequestQueue requestQueue;
    private Object RateListModel;
    private StringRequest request;
    private String JSonURL = "https://api.ratesapi.io/api/latest?base=USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencyconverter);
        //Initialization
        FirstCurrency = (EditText) findViewById(R.id.txtFirstCurrency);
        SecondCurrency = (EditText) findViewById(R.id.txtSecondCurrency);
        sConvert = (Button) findViewById(R.id.btnConvert);
        sRefresh = (Button) findViewById(R.id.btnRefresh);
        ListRate = (RecyclerView) findViewById(R.id.rcListRate);
        requestQueue = Volley.newRequestQueue(this);
        jsonrequest();
//Button Convert
        sConvert.setOnClickListener(v -> {
            RateAdapter adapter = (RateAdapter) ListRate.getAdapter();
            try {
                double value = adapter.getSelectedValue();
                String key = adapter.getKey();
                double source = Double.parseDouble(FirstCurrency.getText().toString());
                SecondCurrency.setText(String.format("%.4f", source * value) + String.format("%5s",key));
            } catch (IllegalAccessException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//Button Refresh
        sRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonrequest();
                SecondCurrency.setText(null);
            }
        });

    }

    private void jsonrequest() {
        request = new StringRequest(Request.Method.GET, JSonURL, result -> {
            try {
                Logger.getLogger("DEBUG").warning(result);
                RateAdapter adapter = new RateAdapter(this, result);
                ListRate.setLayoutManager(new LinearLayoutManager(this));
                ListRate.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(this, "Error.Please try it later", Toast.LENGTH_LONG).show();
        });
        requestQueue.add(request);
    }


}