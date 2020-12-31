package com.example.myfirstapp.luckybankonlinesystem.Class;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.WaitingDialog;
import com.example.myfirstapp.luckybankonlinesystem.LoginActivity;
import com.example.myfirstapp.luckybankonlinesystem.R;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private final Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Not the phone's owner's fingerprint, Please try again", false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Checked fingerprint has been successfully", true);
        nextActivity();
    }

    private void update(String message, boolean isAuthSucceed) {
        TextView tvMessage = (TextView) ((Activity)context).findViewById(R.id.Meesage);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.FingerPrintIcon);

        tvMessage.setText(message);

        if(!isAuthSucceed) {
            tvMessage.setTextColor(ContextCompat.getColor(context, R.color.yellow_dark_DarkMode));
        } else {
            tvMessage.setTextColor(ContextCompat.getColor(context, R.color.green));
            imageView.setImageResource(R.drawable.ic_fingerprint_check);
        }
    }

    public void nextActivity() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
