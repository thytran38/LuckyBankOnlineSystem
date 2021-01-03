package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfirstapp.luckybankonlinesystem.Adapter.RateAdapter;
import com.example.myfirstapp.luckybankonlinesystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

public class CurrencyFragment extends Fragment {

    EditText etFirstCurrency, dtSecondCurrency;
    Button btnConvert;
    TextView tvGoldRate;

    private RecyclerView rvCurrencyRates;
    private RequestQueue requestQueue;
    private static final String CURRENCY_RATES_URL = "https://api.ratesapi.io/api/latest?base=USD";
    private static final String API_KEY = "52ow3c576nkf8c3owt5h57yl9ajpnz491f16r9vq4gvt67to54fdr7uok1b7";
    private static final String GOLD_RATE_BASE_URL = "https://metals-api.com/api/latest";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.currency_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Initialization
        etFirstCurrency = view.findViewById(R.id.txtFirstCurrency);
        dtSecondCurrency = view.findViewById(R.id.txtSecondCurrency);
        btnConvert = view.findViewById(R.id.btnConvert);
        rvCurrencyRates = view.findViewById(R.id.rvCurrencyRates);
        tvGoldRate = view.findViewById(R.id.tvGoldRate);
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this.getContext()));
        jsonRequest();
//Button Convert
        btnConvert.setOnClickListener(v -> {
            RateAdapter adapter = (RateAdapter) rvCurrencyRates.getAdapter();
            try {
                assert adapter != null;
                double value = adapter.getSelectedValue();
                String key = adapter.getKey();
                double source = Double.parseDouble(etFirstCurrency.getText().toString());
                dtSecondCurrency.setText(String.format(Locale.US, "%.4f", source * value).concat(String.format(Locale.US,"%5s",key)));
            } catch (IllegalAccessException e) {
                Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void jsonRequest() {
        StringRequest currencyRequest = new StringRequest(Request.Method.GET, CURRENCY_RATES_URL, result -> {
            try {
                RateAdapter adapter = new RateAdapter(Objects.requireNonNull(this.getContext()), result);
                rvCurrencyRates.setLayoutManager(new LinearLayoutManager(this.getContext()));
                rvCurrencyRates.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(this.getContext(), "Error.Please try it later", Toast.LENGTH_LONG).show();
        });
        final String goldRateUrl = GOLD_RATE_BASE_URL + "?access_key=" + API_KEY + "&base=USD&symbols=xau,xag,btc,eth,xpt";
        StringRequest goldRateRequest = new StringRequest(Request.Method.GET, goldRateUrl, result -> {
            try {
                JSONObject obj = new JSONObject(result).getJSONObject("rates");
                StringBuilder stringBuilder = new StringBuilder();
                String[] gold = getContext().getResources().getStringArray(R.array.gold);
                for (String item : gold) {
                    stringBuilder.append(String.format(Locale.US, "%s: %s\n", item, obj.getString(item)));
                }
                tvGoldRate.setText(stringBuilder.toString());
            } catch (JSONException e) {
                Logger.getLogger("DEBUG").warning("ERROR: " + e.getMessage());
            }
        }, error -> Logger.getLogger("DEBUG").warning("FATAL: " + error.getMessage()));
        requestQueue.add(currencyRequest);
        requestQueue.add(goldRateRequest);
    }
}
