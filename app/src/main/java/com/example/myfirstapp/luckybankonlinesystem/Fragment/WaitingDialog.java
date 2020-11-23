package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.Objects;

public class WaitingDialog extends DialogFragment {
    private final int waitingAnimationId;

    public WaitingDialog(int waitingAnimationId) {
        this.waitingAnimationId = waitingAnimationId;
        this.setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        final View view = inflater.inflate(R.layout.loading_layout, null);
        LottieAnimationView animationView = view.findViewById(R.id.animation_container);
        animationView.setAnimation(waitingAnimationId);
        animationView.setRepeatMode(LottieDrawable.REVERSE);
        return new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setView(view)
                .setCancelable(false)
                .create();
    }
}

