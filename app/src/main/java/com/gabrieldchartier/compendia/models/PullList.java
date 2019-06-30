package com.gabrieldchartier.compendia.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.gabrieldchartier.compendia.util.TempCollection;

import java.util.ArrayList;
import java.util.UUID;

public class PullList implements Parcelable
{
    // Singleton Instance
    private static PullList instance = null;

    private ArrayList<Comic> comics;
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
        comics = new TempCollection().getTempCollection();
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

    public ArrayList<Comic> getComics()
    {
        return comics;
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
