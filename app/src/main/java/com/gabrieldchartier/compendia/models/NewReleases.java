package com.gabrieldchartier.compendia.models;

import androidx.annotation.Nullable;

import com.gabrieldchartier.compendia.util.DateUtilities;
import com.gabrieldchartier.compendia.util.TempCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewReleases
{
    private static final String TAG = "NewReleases";

    private ArrayList<String> weeks;
    private List<Comic> comics;

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
            if(c.getComicID().equals(ID))
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
