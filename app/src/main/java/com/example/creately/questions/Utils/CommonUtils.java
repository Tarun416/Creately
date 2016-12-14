package com.example.creately.questions.Utils;

import android.text.format.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by Alok on 14/12/16.
 */

public class CommonUtils {


    public static String toRelativeTime(final DateTime dateTime) {

        long nowLngTime = System.currentTimeMillis();
        DateTime now = new DateTime();

        long difference = Math.abs(CommonUtils.convertDateTimeToLocal(dateTime).getMillis() - now.getMillis());

        Period period = new Period(CommonUtils.convertDateTimeToLocal(dateTime), now);
        PeriodFormatterBuilder formatterBuilder = new PeriodFormatterBuilder();
        if (difference > DateUtils.YEAR_IN_MILLIS) {
            formatterBuilder.appendYears().appendSuffix(" year");
        } else if (difference > DateUtils.DAY_IN_MILLIS * 30) {
            formatterBuilder.appendMonths().appendSuffix(" month");
        } else if (difference > DateUtils.WEEK_IN_MILLIS) {
            formatterBuilder.appendWeeks().appendSuffix(" week");
        } else if (difference > DateUtils.DAY_IN_MILLIS) {
            formatterBuilder.appendDays().appendSuffix(" day");
        } else if (difference > DateUtils.HOUR_IN_MILLIS) {
            formatterBuilder.appendHours().appendSuffix(" hour");
        } else if (difference > DateUtils.MINUTE_IN_MILLIS) {
            formatterBuilder.appendMinutes().appendSuffix(" minute");
        } else if (difference > DateUtils.SECOND_IN_MILLIS) {
            formatterBuilder.appendSeconds().appendSuffix(" sec");
        }
        String ends = formatterBuilder.printZeroNever().toFormatter().print(period);
        String plural = ends.startsWith("1 ") ? "" : "s";
        ends = ends + plural + " " + "ago";

        return ends;
    }

    private static DateTime convertDateTimeToLocal(DateTime eventDateTime) {

        // get your local timezone
        DateTimeZone localTZ = DateTimeZone.getDefault();

        // convert the input parse datetime to local
        long eventMillsInParseTimeZone = localTZ.convertUTCToLocal(eventDateTime.getMillis());

        return new DateTime(eventMillsInParseTimeZone);
    }
}
