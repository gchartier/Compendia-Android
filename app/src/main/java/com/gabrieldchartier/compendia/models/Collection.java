package com.gabrieldchartier.compendia.models;

import android.content.Context;
import android.support.annotation.Nullable;

import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.util.TempCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Collection
{
    // Constants
    private static final String READ_LIST_NAME = "Read";
    private static final String WANT_LIST_NAME = "Want";
    private static final String FAVORITE_LIST_NAME = "Favorite";

    // Singleton Instance
    private static Collection instance = null;

    // Variables
    private List<Comic> comics;
    private List<ComicList> customLists;
    private ComicList readList;
    private ComicList wantList;
    private ComicList favoriteList;

    // Temp Variables
    private List<Comic> tempRead;
    private List<Comic> tempWant;
    private List<Comic> tempFavorite;

    public static Collection getInstance()
    {
        if(instance == null)
        {
            instance = new Collection();
        }
        return instance;
    }

    private Collection()
    {
        comics = new TempCollection().getTempCollection();
        tempRead = new ArrayList<>();
        tempWant = new ArrayList<>();
        tempFavorite = new ArrayList<>();
        tempRead.add(comics.get(0));
        tempRead.add(comics.get(1));
        tempRead.add(comics.get(2));
        tempRead.add(comics.get(3));
        tempWant.add(comics.get(0));
        tempWant.add(comics.get(1));
        tempWant.add(comics.get(2));
        tempWant.add(comics.get(3));
        tempFavorite.add(comics.get(0));
        tempFavorite.add(comics.get(1));
        tempFavorite.add(comics.get(2));
        tempFavorite.add(comics.get(3));

        readList = new ComicList(READ_LIST_NAME, tempRead);
        wantList = new ComicList(WANT_LIST_NAME, tempWant);
        favoriteList = new ComicList(FAVORITE_LIST_NAME, tempFavorite);
    }

    public List<Comic> getComics()
    {
        return comics;
    }

    @Nullable
    public Comic getComicByID(UUID ID)
    {
        for(Comic c : comics)
        {
            if(c.getID() == ID)
                return c;
        }
        return null;
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

    public boolean comicIsInCustomList(Comic comic, String listName)
    {
        ComicList list = getCustomListByName(listName);
        if(list != null && list.comicIsInList(comic))
            return true;
        else
            return false;
    }

    public List<ComicList> getCustomLists()
    {
        if(customLists == null)
            return new ArrayList<>();
        else
            return customLists;
    }

    public void setCustomLists(List<ComicList> customLists)
    {
        this.customLists = customLists;
    }

    @Nullable
    private ComicList getCustomListByName(String listName)
    {
        ComicList list = null;
        for(ComicList l : customLists)
            if(l.getListName().equals(listName))
                list = l;
        return list;
    }

    public ArrayList<ComicList> getCustomListsComicIsIn(Comic comic)
    {
        ArrayList<ComicList> listsComicIsIn = new ArrayList<>();
        for(ComicList list : customLists)
            if(comicIsInCustomList(comic, list.getListName()))
                listsComicIsIn.add(list);
        return listsComicIsIn;
    }

    public ComicList getReadList()
    {
        return readList;
    }

    public void setReadList(ComicList readList)
    {
        this.readList = readList;
    }

    public ComicList getWantList()
    {
        return wantList;
    }

    public void setWantList(ComicList wantList)
    {
        this.wantList = wantList;
    }

    public ComicList getFavoriteList()
    {
        return favoriteList;
    }

    public void setFavoriteList(ComicList favoriteList)
    {
        this.favoriteList = favoriteList;
    }

    public boolean comicIsRead(Comic comic)
    {
        return readList.comicIsInList(comic);
    }

    public boolean comicIsWanted(Comic comic)
    {
        return wantList.comicIsInList(comic);
    }

    public boolean comicIsFavorited(Comic comic)
    {
        return favoriteList.comicIsInList(comic);
    }
}
