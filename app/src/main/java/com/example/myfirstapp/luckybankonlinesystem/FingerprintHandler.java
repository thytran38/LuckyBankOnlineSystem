package com.example.myfirstapp.luckybankonlinesystem;

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

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Not the phone's owner's fingerprint, Please try again ", false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Checked fingerprint has been successfully", true);
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    private void update(String s, boolean b) {
        TextView message = (TextView) ((Activity)context).findViewById(R.id.Meesage);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.FingerPrintIcon);

        message.setText(s);

        if(b == false) {
            message.setTextColor(ContextCompat.getColor(context, R.color.yellow_dark_DarkMode));
        } else {
            message.setTextColor(ContextCompat.getColor(context, R.color.green));
            imageView.setImageResource(R.drawable.ic_fingerprint_check);
        }
    }
}