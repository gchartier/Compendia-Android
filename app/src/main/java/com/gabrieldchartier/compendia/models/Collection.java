package com.gabrieldchartier.compendia.models;

import android.support.annotation.Nullable;

import com.gabrieldchartier.compendia.util.TempCollection;
import java.util.List;

public class Collection
{
    private List<Comic> comics;
    private List<ComicList> userComicLists;

    public Collection()
    {
        comics = new TempCollection().getTempCollection();
    }

    public List<Comic> getComics()
    {
        return comics;
    }

    public void setComics(List<Comic> comics)
    {
        this.comics = comics;
    }

    public boolean comicIsInCollection(Comic comic)
    {
        for(Comic c : comics)
            if(comic.getID().equals(c.getID()))
                return true;

        return false;
    }

    public boolean comicIsInList(Comic comic, String listName)
    {
        ComicList list = getComicListByName(listName);
        if(list != null && list.comicIsInList(comic))
            return true;
        else
            return false;
    }

    public List<ComicList> getUserComicLists()
    {
        return userComicLists;
    }

    public void setUserComicLists(List<ComicList> userComicLists)
    {
        this.userComicLists = userComicLists;
    }

    @Nullable
    private ComicList getComicListByName(String listName)
    {
        ComicList list = null;
        for(ComicList l : userComicLists)
            if(l.getListName().equals(listName))
                list = l;
        return list;
    }
}
