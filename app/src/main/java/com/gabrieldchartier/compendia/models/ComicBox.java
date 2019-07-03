package com.gabrieldchartier.compendia.models;

import java.util.List;

public class ComicBox
{
    public static final int READ_BOX_NUMBER = 1;
    public static final int WANT_BOX_NUMBER = 2;
    public static final int FAVORITES_BOX_NUMBER = 3;
    public static final int NUM_STATIC_BOXES = 3; // The number of static boxes for a user

    private String boxName;
    private List<Comic> comicsInBox;

    public ComicBox(String boxName, List<Comic> comicsInBox)
    {
        this.boxName = boxName;
        this.comicsInBox = comicsInBox;
    }

    public String getBoxName()
    {
        return boxName;
    }

    public void setBoxName(String boxName)
    {
        this.boxName = boxName;
    }

    public List<Comic> getComicsInBox()
    {
        return comicsInBox;
    }

    public void setComicsInBox(List<Comic> comicsInBox)
    {
        this.comicsInBox = comicsInBox;
    }

    public boolean comicIsInBox(Comic comic)
    {
        for(Comic c : comicsInBox)
            if(c.getID() == comic.getID())
                return true;
        return false;
    }
}
