package com.gabrieldchartier.compendia.models;

import android.support.annotation.Nullable;
import com.gabrieldchartier.compendia.util.TempCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Collection
{
    // Constants
    private static final String READ_BOX_NAME = "Read";
    private static final String WANT_BOX_NAME = "Want";
    private static final String FAVORITE_BOX_NAME = "Favorite";

    // Singleton Instance
    private static Collection instance = null;

    // Variables
    private List<Comic> comics;
    private List<ComicBox> customBoxes;
    private ComicBox readBox;
    private ComicBox wantBox;
    private ComicBox favoriteBox;
    private List<ComicReview> reviews;//todo

    public static Collection getInstance()
    {
        if(instance == null)
        {
            instance = new Collection();
        }
        return instance;
    }

    //TODO retrieve from repository
    private Collection()
    {
        List<Comic> tempRead;
        List<Comic> tempWant;
        List<Comic> tempFavorite;

        comics = new TempCollection().getTempCollection();
        tempRead = new ArrayList<>();
        tempWant = new ArrayList<>();
        tempFavorite = new ArrayList<>();
        reviews = new ArrayList<>();
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

        readBox = new ComicBox(READ_BOX_NAME, tempRead);
        wantBox = new ComicBox(WANT_BOX_NAME, tempWant);
        favoriteBox = new ComicBox(FAVORITE_BOX_NAME, tempFavorite);

        reviews.add(new ComicReview(UUID.randomUUID(), comics.get(0).getID(),
                "Great read! I loved how this one wadha adkhwhd ajw hjwhwjawdhawkjd  j wdak h wjkdhkj ah!",
                "Loved it!", false, 6));
        reviews.add(new ComicReview(UUID.randomUUID(), comics.get(1).getID(),
                "I'm really starting to love this series. Great character development in this issue.",
                "Can't wait for more", false, 10));
        reviews.add(new ComicReview(UUID.randomUUID(), comics.get(0).getID(),
                "I can't believe my favorite character died in an annoying way! The writers suck.",
                "BOOOO!", true, 1));
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
            if(c.getID().equals(ID))
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

    public boolean comicIsInCustomBox(Comic comic, String boxName)
    {
        ComicBox box = getCustomListByName(boxName);
        if(box != null && box.comicIsInBox(comic))
            return true;
        else
            return false;
    }

    public List<ComicBox> getCustomBoxes()
    {
        if(customBoxes == null)
            return new ArrayList<>();
        else
            return customBoxes;
    }

    public void setCustomBoxes(List<ComicBox> customBoxes)
    {
        this.customBoxes = customBoxes;
    }

    @Nullable
    private ComicBox getCustomListByName(String boxName)
    {
        ComicBox box = null;
        for(ComicBox l : customBoxes)
            if(l.getBoxName().equals(boxName))
                box = l;
        return box;
    }

    public ArrayList<ComicBox> getCustomBoxesComicIsIn(Comic comic)
    {
        ArrayList<ComicBox> boxesComicIsIn = new ArrayList<>();
        for(ComicBox box : customBoxes)
            if(comicIsInCustomBox(comic, box.getBoxName()))
                boxesComicIsIn.add(box);
        return boxesComicIsIn;
    }

    public ComicBox getReadBox()
    {
        return readBox;
    }

    public void setReadBox(ComicBox readBox)
    {
        this.readBox = readBox;
    }

    public ComicBox getWantBox()
    {
        return wantBox;
    }

    public void setWantBox(ComicBox wantBox)
    {
        this.wantBox = wantBox;
    }

    public ComicBox getFavoriteBox()
    {
        return favoriteBox;
    }

    public void setFavoriteBox(ComicBox favoriteBox)
    {
        this.favoriteBox = favoriteBox;
    }

    public boolean comicIsRead(Comic comic)
    {
        return readBox.comicIsInBox(comic);
    }

    public boolean comicIsWanted(Comic comic)
    {
        return wantBox.comicIsInBox(comic);
    }

    public boolean comicIsFavorite(Comic comic)
    {
        return favoriteBox.comicIsInBox(comic);
    }

    public List<ComicReview> getReviews()
    {
        return reviews;
    }

    public void setReviews(List<ComicReview> reviews)
    {
        this.reviews = reviews;
    }
}
