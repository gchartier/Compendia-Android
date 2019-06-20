package com.gabrieldchartier.compendia.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class PullList implements Parcelable
{
    private ArrayList<Comic> comics;
    private ArrayList<String> weeks;

    public PullList(ArrayList<Comic> comics, ArrayList<String> weeks)
    {
        this.comics = comics;
        this.weeks = weeks;
    }

    public ArrayList<Comic> getComics()
    {
        return comics;
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
