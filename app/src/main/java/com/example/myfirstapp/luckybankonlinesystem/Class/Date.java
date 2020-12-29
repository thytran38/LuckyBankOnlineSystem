package com.example.myfirstapp.luckybankonlinesystem.Class;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Date {
    private static GregorianCalendar calendar;
    private java.util.Date date;

    private Date() {
        calendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("ICT"));
        date = new java.util.Date(calendar.getTimeInMillis());
    }

    private Date(int day, int month, int year, int hour, int minute, int second) throws IllegalArgumentException {
        this();
        if (!isValidDate(day, month, year))
            throw new IllegalArgumentException(String.format(Locale.US,
                    "%d/%d/%d is not a valid date", day, month, year));
        if (!isValidTime(hour, minute, second))
            throw new IllegalArgumentException(String.format(Locale.US,
                    "%d:%d:%d is not a valid time", hour, minute, second));
        calendar.clear();
        calendar.set(year, month, day);
        date.setTime(calendar.getTimeInMillis());
    }

    private Date(long epochSecond) {
        this();
        date.setTime(epochSecond * 1000);
        calendar.setTime(date);
    }

    private Date(String formattedTimeStr, boolean includeTime) throws ParseException {
        this();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US);
        date = simpleDateFormat.parse(formattedTimeStr + " 00:00:00");
    }

    public static Date getInstance() {
        return new Date();
    }

    public static Date getInstance(int day, int month, int year) {
        return new Date(day, month, year, 0, 0, 0);
    }

    public static Date getInstance(int day, int month, int year, int hour, int minute, int second) {
        return new Date(day, month, year, hour, minute, second);
    }

    public static Date getInstance(long epochSecond) {
        return new Date(epochSecond);
    }

    public static Date getInstance(String formattedTimeStr, boolean includeTime) throws ParseException {return new Date(formattedTimeStr, includeTime);}

    public static boolean isValidDate(int day, int month, int year) {
        if (month > 12 || month <= 0)
            return false;
        return day > 0 && day <= getDayOfMonth(month, year);
    }

    public static boolean isValidTime(int hour, int minute, int second) {
        return (hour >= 0 && hour <= 23)
                && (minute >= 0 && minute <= 59)
                && second >= 0 && second <= 59;
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
    public String toString(boolean includeTime) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy" + (includeTime ? " hh:mm:ss" : ""), Locale.US);
        return format.format(date);
    }

    @NonNull
    @Override
    public String toString() {
        return toString(false);
    }
}
