package com.gabrieldchartier.compendia.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class ComicCreator implements Parcelable
{
    private UUID ID;
    private String name;
    private String[] creatorTypes;

    public ComicCreator(UUID ID, String name, String[] creatorTypes)
    {
        this.ID = ID;
        this.name = name;
        this.creatorTypes = creatorTypes;
    }

    public UUID getID()
    {
        return ID;
    }

    public void setID(UUID ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCreatorTypes()
    {
        StringBuilder types = new StringBuilder();
        if(creatorTypes.length >= 1)
            for(String type : creatorTypes)
                types.append(" - ").append(type);
        else
            types.append("");

        return types.toString().substring(2);
    }

    public void setCreatorType(String[] creatorTypes)
    {
        this.creatorTypes = creatorTypes;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }
}
