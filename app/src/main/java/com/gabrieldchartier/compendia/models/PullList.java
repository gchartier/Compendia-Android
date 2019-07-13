package com.gabrieldchartier.compendia.models;

import android.os.Parcel;
import android.os.Parcelable;
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

import static android.support.constraint.Constraints.TAG;

public class PullList implements Parcelable
{
    private static final String TAG = "PullList";

    // Singleton Instance
    private static PullList instance = null;

    private List<Comic> comics;
    private ArrayList<String> weeks;

    public static PullList getInstance()
    {
        if(instance == null)
        {
            instance = new PullList();
        }
        return instance;
    }

    //TODO retrieve from repository
    private PullList()
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

    //TODO test that this method returns the correct comics or none if applicable
    public List<Comic> getClosestWeek()
    {
        return DateUtilities.getClosestWeek(comics, weeks);
    }

    public ArrayList<String> getWeeks()
    {
        return weeks;
    }

    public void setComics(ArrayList<Comic> comics)
    {
        this.comics = comics;
    }

    protected PullList(Parcel in)
    {
        in.readTypedList(comics, Comic.CREATOR);
        in.readStringList(weeks);
    }

    public static final Parcelable.Creator<PullList> CREATOR = new Parcelable.Creator<PullList>()
    {
        @Override
        public PullList createFromParcel(Parcel in)
        {
            return new PullList(in);
        }

        @Override
        public PullList[] newArray(int size)
        {
            return new PullList[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeTypedList(comics);
        dest.writeStringList(weeks);
    }
}
