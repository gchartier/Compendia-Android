package com.gabrieldchartier.compendia.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName="creators")
public class ComicCreator implements Parcelable
{
    @PrimaryKey
    private int id;
    @ColumnInfo(name="creatorID")
    private UUID creatorID;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="creatorTypes")
    private String[] creatorTypes;

    public ComicCreator(UUID creatorID, String name, String[] creatorTypes)
    {
        this.creatorID = creatorID;
        this.name = name;
        this.creatorTypes = creatorTypes;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public UUID getCreatorID()
    {
        return creatorID;
    }

    public void setCreatorID(UUID creatorID)
    {
        this.creatorID = creatorID;
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
        dest.writeString(name);
        dest.writeStringArray(creatorTypes);
    }

    protected ComicCreator(Parcel in)
    {
        name = in.readString();
        creatorTypes = in.createStringArray();
    }

    public static final Creator<ComicCreator> CREATOR = new Creator<ComicCreator>()
    {
        @Override
        public ComicCreator createFromParcel(Parcel in)
        {
            return new ComicCreator(in);
        }

        @Override
        public ComicCreator[] newArray(int size)
        {
            return new ComicCreator[size];
        }
    };
}
