package com.gabrieldchartier.compendia.models;

import java.util.UUID;

public class ComicCharacter
{
    private UUID ID;
    private String name;

    public ComicCharacter(UUID ID, String name)
    {
        this.ID = ID;
        this.name = name;
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
}
