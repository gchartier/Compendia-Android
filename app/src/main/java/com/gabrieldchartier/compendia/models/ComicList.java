package com.gabrieldchartier.compendia.models;

import java.util.List;

public class ComicList
{
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
