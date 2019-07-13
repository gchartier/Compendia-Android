package com.gabrieldchartier.compendia.util;

import android.util.Log;

import com.gabrieldchartier.compendia.models.Comic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtilities
{
    private static final String TAG = "DateUtilities";

    private static SimpleDateFormat comicReleaseDateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

    public static boolean isDateInCurrentWeek(Date date)
    {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

    public static SimpleDateFormat getComicReleaseDateFormat()
    {
        return comicReleaseDateFormat;
    }

    public static List<Comic> getClosestWeek(List<Comic> comics, List<String> weeks)
    {
        DateFormat format = DateUtilities.getComicReleaseDateFormat();
        List<Date> dates = new ArrayList<>();
        List<Comic> closestWeeksComics = new ArrayList<>();
        Date closestWeek;

        try
        {
            for(String d : weeks)
                dates.add(format.parse(d));
        }
        catch(ParseException error)
        {
            Log.e(TAG,"Error occurred while trying to get the most recent week: " + error);
        }

        closestWeek = Collections.min(dates);

        Log.d(TAG, format.format(closestWeek));

        if(DateUtilities.isDateInCurrentWeek(closestWeek))
            for(Comic c : comics)
                if(c.getReleaseDate().equals(format.format(closestWeek)))
                    closestWeeksComics.add(c);

        Log.d(TAG, closestWeeksComics.toString());
        return closestWeeksComics;
    }

    public static Date getCurrentDateTime()
    {
        return Calendar.getInstance().getTime();
    }
}
