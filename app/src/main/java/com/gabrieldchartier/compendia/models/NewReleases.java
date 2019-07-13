package com.gabrieldchartier.compendia.models;

import android.support.annotation.Nullable;
import android.util.Log;
import com.gabrieldchartier.compendia.util.DateUtilities;
import com.gabrieldchartier.compendia.util.TempCollection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class NewReleases
{
    private static final String TAG = "NewReleases";

    // Singleton Instance
    private static NewReleases instance = null;
    private ArrayList<String> weeks;

    private List<Comic> comics;

    public static NewReleases getInstance()
    {
        if(instance == null)
        {
            instance = new NewReleases();
        }
        return instance;
    }

    //TODO retrieve from repository
    private NewReleases()
    {
        comics = new TempCollection().comics;
        weeks = new ArrayList<>();
        for(Comic c : comics)
            if(!weeks.contains(c.getReleaseDate()))
                weeks.add(c.getReleaseDate());
    }

    @Nullable
    public Comic getComicByID(UUID ID)
    {
        for(Comic c : comics)
        {
            if(c.getID().equals(ID))
                return c;
        }
        return null;
    }

    public List<Comic> getComics()
    {
        return comics;
    }

    public List<Comic> getThisWeek()
    {
        return DateUtilities.getClosestWeek(comics, weeks);
    }
}
