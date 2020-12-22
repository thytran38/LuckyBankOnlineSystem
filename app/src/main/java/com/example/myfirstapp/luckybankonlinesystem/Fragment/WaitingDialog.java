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
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class WaitingDialog extends DialogFragment {

    public static Object Builder;
    private final int waitingAnimationId;
    private int waitingTime;
    private Task waitingTask;
    private LottieAnimationView animationView;

    public WaitingDialog(int waitingAnimationId) {
        this.setCancelable(false);
        this.waitingAnimationId = waitingAnimationId;
        this.waitingTime = 5;
    }

    public WaitingDialog(int waitingAnimationId, int waitingTime) {
        this(waitingAnimationId);
        this.waitingTime = waitingTime;
    }

    public WaitingDialog(int waitingAnimationId, Task waitingTask) {
        this(waitingAnimationId);
        this.waitingTask = waitingTask;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        final View view = inflater.inflate(R.layout.loading_layout, null);
        animationView = view.findViewById(R.id.animation_container);
        animationView.setAnimation(waitingAnimationId);
        animationView.setRepeatMode(LottieDrawable.RESTART);
        if (waitingTask != null) {
            waitingTask.addOnCompleteListener(task -> cancel());
        } else {
            animationView.postDelayed(this::cancel, waitingTime * 1000);
        }
        return new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setView(view)
                .setCancelable(false)
                .create();
    }

    private void cancel() {
        animationView.cancelAnimation();
        this.dismiss();
    }
}
