package com.gabrieldchartier.compendia.models;

import java.util.UUID;

public class ComicCreator
{
    private UUID ID;
    private String name;
    private int creatorType;

    public ComicCreator(UUID ID, String name, int creatorType)
    {
        this.ID = ID;
        this.name = name;
        this.creatorType = creatorType;
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

    public int getCreatorType()
    {
        return creatorType;
    }

    public void setCreatorType(int creatorType)
    {
        this.creatorType = creatorType;
    }
}
