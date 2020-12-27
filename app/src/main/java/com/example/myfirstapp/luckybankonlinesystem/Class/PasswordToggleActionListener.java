package com.example.myfirstapp.luckybankonlinesystem.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.logging.Logger;

public class PasswordToggleActionListener implements View.OnTouchListener {

    private boolean isShowing = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            EditText et = (EditText) v;
            int iconWidth = et.getCompoundDrawables()[2].getBounds().width();
            if (event.getRawX() >= (et.getRight() - iconWidth)) {
                Drawable drawable = ContextCompat.getDrawable(et.getContext(), isShowing ? R.drawable.ic_eye : R.drawable.ic_unvisible);
                if (drawable != null) {
                    drawable.setBounds(0,0, 45, 45);
                    et.setCompoundDrawables(null, null, drawable, null);
                }
                TransformationMethod method = isShowing ? PasswordTransformationMethod.getInstance() :
                        HideReturnsTransformationMethod.getInstance();
                et.setTransformationMethod(method);
                isShowing = !isShowing;
            }
        }
        return false;
    }
}
