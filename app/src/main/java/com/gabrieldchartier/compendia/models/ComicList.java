package com.gabrieldchartier.compendia.models;

import java.util.List;

public class ComicList
{
    public static final int READ_LIST_NUMBER = 1;
    public static final int WANT_LIST_NUMBER = 2;
    public static final int FAVORITES_LIST_NUMBER = 3;
    public static final int NUM_STATIC_LISTS = 3; // The number of static lists for a user

    private String listName;
    private List<Comic> comicsInList;

    public ComicList(String listName, List<Comic> comicsInList)
    {
        this.listName = listName;
        this.comicsInList = comicsInList;
    }

    public String getListName()
    {
        return listName;
    }

    public void setListName(String listName)
    {
        this.listName = listName;
    }

    public List<Comic> getComicsInList()
    {
        return comicsInList;
    }

    public void setComicsInList(List<Comic> comicsInList)
    {
        this.comicsInList = comicsInList;
    }

    public boolean comicIsInList(Comic comic)
    {
        for(Comic c : comicsInList)
            if(c.getID() == comic.getID())
                return true;
        return false;
    }
}
