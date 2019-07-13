package com.gabrieldchartier.compendia.models;

import com.gabrieldchartier.compendia.util.DateUtilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ComicBox implements Comparable<ComicBox>
{
    public static final int READ_BOX_NUMBER = 1;
    public static final int WANT_BOX_NUMBER = 2;
    public static final int FAVORITES_BOX_NUMBER = 3;
    public static final int NUM_STATIC_BOXES = 3; // The number of static boxes for a user
    public static final String READ_BOX_NAME = "Read";
    public static final String WANT_BOX_NAME = "Want";
    public static final String FAVORITE_BOX_NAME = "Favorites";

    private String boxName;
    private List<Comic> comicsInBox;
    private Date lastUpdated;

    public ComicBox(String boxName, List<Comic> comicsInBox, Date lastUpdated)
    {
        this.boxName = boxName;
        this.comicsInBox = comicsInBox;
        this.lastUpdated = lastUpdated;
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

    public boolean containsComic(Comic comic)
    {
        for(Comic c : comicsInBox)
            if(c.getID() == comic.getID())
                return true;
        return false;
    }

    @Override
    public int compareTo(ComicBox box)
    {
        return getLastUpdated().compareTo(box.getLastUpdated());
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public void addComic(Comic comic)
    {
        comicsInBox.add(comic);
        lastUpdated = DateUtilities.getCurrentDateTime();
    }

    public void removeComic(Comic comic)
    {
        comicsInBox.remove(comic);
        lastUpdated = DateUtilities.getCurrentDateTime();
    }
}
