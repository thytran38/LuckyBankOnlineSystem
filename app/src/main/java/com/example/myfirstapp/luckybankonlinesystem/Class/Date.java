package com.example.myfirstapp.luckybankonlinesystem.Class;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Date {
    private static GregorianCalendar calendar;
    private static Date instance;

    private Date() {
        calendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("ICT"));
    }

    private Date(int day, int month, int year) throws IllegalArgumentException {
        this();
        if (isValidDate(day, month, year))
            throw new IllegalArgumentException(String.format(Locale.ENGLISH,
                    "%d/%d/%d is not a valid date", day, month, year));
    }

    private Date(long epochSecond) {
        this();
        calendar.setTimeInMillis(epochSecond * 1000);
    }

    public static Date getInstance() {
        if (instance == null)
            instance = new Date();
        return instance;
    }

    public static Date getInstance(int day, int month, int year) {
        if (instance == null)
            instance = new Date(day, month, year);
        return instance;
    }

    public static Date getInstance(long epochSecond) {
        if (instance == null)
            instance = new Date(epochSecond);
        return instance;
    }

    public static boolean isValidDate(int day, int month, int year) {
        if (month > 12 || month <= 0)
            return false;
        return day > 0 && day <= getDayOfMonth(month, year);
    }

    public static int getDayOfMonth(int month, int year) {
        if (month == 2)
            return isLeapYear(year) ? 29 : 28;
        if (month <= 7)
            return 30 + (month % 2);
        else
            return 31 - (month % 2);
    }

    public static boolean isLeapYear(int year) {
        GregorianCalendar tempCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        return tempCalendar.isLeapYear(year);
    }

    public long getEpochSecond() {
        return calendar.getTimeInMillis() / 1000;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return format.format(calendar.getTime());
    }
}
