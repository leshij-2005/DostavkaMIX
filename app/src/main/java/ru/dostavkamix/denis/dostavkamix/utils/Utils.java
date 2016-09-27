package ru.dostavkamix.denis.dostavkamix.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by denis on 26.09.16.
 */

public class Utils {

    private static Calendar calendar = Calendar.getInstance();

    public static String formatTime(String date, String fromPattern, String inPattern) {
        try {
            return new SimpleDateFormat(inPattern, Locale.getDefault()).format(new SimpleDateFormat(fromPattern, Locale.getDefault()).parse(date));
        } catch (ParseException e) {
            return date;
        }
    }

    public static boolean isCurrentDate(int year, int month, int dayOfMonth) {
        return (year == getCurrentYear() && month == getCurrentMonth() && dayOfMonth == getCurrentDayOfMonth());
    }

    public static int getCurrentYear() { return calendar.get(Calendar.YEAR); }

    public static int getCurrentMonth() { return calendar.get(Calendar.MONTH); }

    public static int getCurrentDayOfMonth() { return calendar.get(Calendar.DAY_OF_MONTH); }

    public static int getCurrentHourOfDay() { return calendar.get(Calendar.HOUR_OF_DAY); }

    public static int getCurrentMinute() { return calendar.get(Calendar.MINUTE); }
}
