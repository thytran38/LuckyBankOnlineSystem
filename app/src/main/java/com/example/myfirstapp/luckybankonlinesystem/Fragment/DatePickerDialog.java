package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.Calendar;

public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    public interface OnDatePickedListener {
        void onDateOk(int date, int month, int year);
        void onDateError(int date, int month, int year);
    }

    public interface IsValidDateCallback {
        boolean isValidDate(int date, int month, int year);
    }

    private final OnDatePickedListener listener;
    private final IsValidDateCallback callback;

    public DatePickerDialog(OnDatePickedListener listener, IsValidDateCallback callback) {
        this.listener = listener;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return new android.app.DatePickerDialog(getContext(), this, year, 1, 1);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (callback.isValidDate(dayOfMonth, month + 1, year)) {
            listener.onDateOk(dayOfMonth, month + 1, year);
        } else {
            listener.onDateError(dayOfMonth, month + 1, year);
        }
    }
}
