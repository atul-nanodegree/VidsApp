package com.vidsapp;

import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by atul.
 */
public class TimeDurationUtil {

    private static String TAG = "TimeDurationUtil";

    private static final String YEAR_AGO = "year ago";

    private static final String YEARS_AGO = "years ago";

    private static final String MONTH_AGO = "month ago";

    private static final String MONTHS_AGO = "months ago";

    private static final String WEEK_AGO = "week ago";

    private static final String WEEKS_AGO = "weeks ago";

    private static final String DAY_AGO = "day ago";

    private static final String DAYS_AGO = "days ago";

    private static final String HOURS_AGO = "hours ago";

    private static final String MINUTES_AGO = "minutes ago";

    private static final String SECONDS_AGO = "seconds ago";

    private static final String ZERO = "0";

    public static String publishTimeNew(DateTime publishTime) {


        StringBuilder stringAfterFormat = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        String in = publishTime.toString();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {

            DateTime t = new DateTime(System.currentTimeMillis());
            long value = t.getValue() - publishTime.getValue();

            int seconds = (int) (value / 1000) % 60;
            int minutes = (int) ((value / (1000 * 60)) % 60);
            int hours = (int) ((value / (1000 * 60 * 60)) % 24);
            long days = value / (24 * 60 * 60 * 1000);
            // int week   = (int) ((value / (24 * 60 * 60 * 1000*7)) % 4);
            int week = (int) ((value / (24 * 60 * 60 * 1000 * 7)) % 4);
            //  int months   = (int) ((value / (24 * 60 * 60 * 1000*7*4)) % 12);
            Double monthsDouble = Math.floor((value / 2592000) / 1000);
            String months = Integer.toString(monthsDouble.intValue());
            Double yearsDouble = Math.floor((value / 2592000) / 12000);
            String years = Integer.toString(yearsDouble.intValue());

            if( days != 0){
                if(days==1){
                    stringAfterFormat.append(days).append(" ").append(DAY_AGO);

                }
                else{
                    stringAfterFormat.append(days).append(" ").append(DAYS_AGO);
                }

            } else if (days == 0 && hours != 0) {
                stringAfterFormat.append(hours).append(" ").append(HOURS_AGO);
            } else if ( days == 0 && hours == 0 && minutes != 0) {
                stringAfterFormat.append(minutes).append(" ").append(MINUTES_AGO);
            } else if (days == 0 && hours == 0 && minutes == 0 && seconds != 0) {
                stringAfterFormat.append(seconds).append(" ").append(SECONDS_AGO);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return stringAfterFormat.toString();
    }
}
