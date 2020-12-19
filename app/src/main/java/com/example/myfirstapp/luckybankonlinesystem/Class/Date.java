package com.example.myfirstapp.luckybankonlinesystem.Class;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Date {
    private static GregorianCalendar calendar;
    private final java.util.Date date;

    private Date() {
        calendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("ICT"));
        date = new java.util.Date(calendar.getTimeInMillis());
    }

    private Date(int day, int month, int year) throws IllegalArgumentException {
        this();
        if (!isValidDate(day, month, year))
            throw new IllegalArgumentException(String.format(Locale.US,
                    "%d/%d/%d is not a valid date", day, month, year));
        calendar.clear();
        calendar.set(year, month, day);
        date.setTime(calendar.getTimeInMillis());
    }

    private Date(long epochSecond) {
        this();
        date.setTime(epochSecond * 1000);
    }

    public static Date getInstance() {
        return new Date();
    }

    public static Date getInstance(int day, int month, int year) {
        return new Date(day, month, year);
    }

    public static Date getInstance(long epochSecond) {
        return new Date(epochSecond);
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
        return date.getTime() / 1000;
    }

    public int getDate() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return format.format(date);
    }
}
