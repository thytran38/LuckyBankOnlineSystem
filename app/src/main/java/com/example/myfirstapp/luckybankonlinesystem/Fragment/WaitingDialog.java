package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class WaitingDialog extends DialogFragment {

    private final int waitingAnimationId;
    private int waitingTime;
    private Task waitingTask;
    private LottieAnimationView animationView;
    private final Context context;

    public WaitingDialog(Context context, int waitingAnimationId) {
        this.context = context;
        this.setCancelable(false);
        this.waitingAnimationId = waitingAnimationId;
        this.waitingTime = 5;
    }

    public WaitingDialog(Context context, int waitingAnimationId, int waitingTime) {
        this(context, waitingAnimationId);
        this.waitingTime = waitingTime;
    }

    public WaitingDialog(Context context, int waitingAnimationId, Task waitingTask) {
        this(context, waitingAnimationId);
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

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = 200;
        params.height = 200;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.loading_bg);
        getDialog().getWindow().setBackgroundDrawable(drawable);
    }

    private void cancel() {
        animationView.cancelAnimation();
        this.dismiss();
    }
}
