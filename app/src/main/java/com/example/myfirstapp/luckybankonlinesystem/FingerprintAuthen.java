//package com.example.myfirstapp.luckybankonlinesystem;
//
//import android.Manifest;
//import android.app.KeyguardManager;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.hardware.fingerprint.FingerprintManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import java.security.KeyStore;
//
//import javax.crypto.Cipher;
//
//public class FingerprintAuthen extends AppCompatActivity {
//    private KeyStore keyStore;
//    // Variable used for storing the key in the Android Keystore container
//    private static final String KEY_NAME = "androidHive";
//    private Cipher cipher;
//    private TextView textView;
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fingerprint_user);
//        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
//
//        if(!fingerprintManager.isHardwareDetected()){
//            /**
//             * An error message will be displayed if the device does not contain the fingerprint hardware.
//             * However if you plan to implement a default authentication method,
//             * you can redirect the user to a default authentication activity from here.
//             * Example:
//             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
//             * startActivity(intent);
//             */
//            // textView.setText("Your Device does not have a Fingerprint Sensor");
//
//            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
//            startActivity(i);
//        }else {
//            // Checks whether fingerprint permission is set on manifest
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
//                textView.setText("Fingerprint authentication permission not enabled");
//            } else {
//                // Check whether at least one fingerprint is registered
//                if (!fingerprintManager.hasEnrolledFingerprints()) {
//                    textView.setText("Register at least one fingerprint in Settings");
//                } else {
//                    // Checks whether lock screen security is enabled or not
//                    if (!keyguardManager.isKeyguardSecure()) {
//                        textView.setText("Lock screen security not enabled in Settings");
//                    } else {
//                        generateKey();
//
//
//                        if (cipherInit()) {
//                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
//                            FingerprintHandler helper = new FingerprintHandler(this);
//                            helper.startAuth(fingerprintManager, cryptoObject);
//                        }
//                    }
//                }
//            }
//        }
//
//
//
//    }
//}
