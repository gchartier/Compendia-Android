package com.gabrieldchartier.compendia.models;

import android.support.annotation.Nullable;
import com.gabrieldchartier.compendia.util.TempCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Collection
{
    // Singleton Instance
    private static Collection instance = null;

    // Variables
    private List<Comic> comics;
    private List<ComicBox> comicBoxes;
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
        comicBoxes = new ArrayList<>();
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

        // TODO get actual times from db
        comicBoxes.add(new ComicBox(ComicBox.READ_BOX_NAME, tempRead, Calendar.getInstance().getTime()));
        comicBoxes.add(new ComicBox(ComicBox.WANT_BOX_NAME, tempWant, Calendar.getInstance().getTime()));
        comicBoxes.add(new ComicBox(ComicBox.FAVORITE_BOX_NAME, tempFavorite, Calendar.getInstance().getTime()));

        reviews.add(new ComicReview(UUID.randomUUID(), comics.get(0).getID(),
                "Great read! I loved how this one wadha adkhwhd ajw hjwhwjawdhawkjd  j wdak h wjkdhkj ah!",
                "Loved it!", false, 6));
        reviews.add(new ComicReview(UUID.randomUUID(), comics.get(1).getID(),
                "I'm really starting to love this series. Great character development in this issue.",
                "Can't wait for more", false, 10));
        reviews.add(new ComicReview(UUID.randomUUID(), comics.get(0).getID(),
                "I can't believe my favorite character died in an annoying way! The writers suck.",
                "BOOOO!", true, 1));

        // Sort the comic boxes by last updated
        Collections.sort(comicBoxes);
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

    public boolean comicIsInBox(Comic comic, String boxName)
    {
        ComicBox box = getComicBoxByName(boxName);
        return box != null && box.containsComic(comic);
    }

    public List<ComicBox> getComicBoxes()
    {
        if(comicBoxes == null)
            return new ArrayList<>();
        else
            return comicBoxes;
    }

    public void setComicBoxes(List<ComicBox> comicBoxes)
    {
        this.comicBoxes = comicBoxes;
    }

    public List<ComicBox> getCustomComicBoxes()
    {
        List<ComicBox> customBoxes = comicBoxes;
        customBoxes.remove(getComicBoxByName(ComicBox.READ_BOX_NAME));
        customBoxes.remove(getComicBoxByName(ComicBox.FAVORITE_BOX_NAME));
        customBoxes.remove(getComicBoxByName(ComicBox.WANT_BOX_NAME));

        return customBoxes;
    }

    @Nullable
    public ComicBox getComicBoxByName(String boxName)
    {
        ComicBox box = null;
        for(ComicBox b : comicBoxes)
            if(b.getBoxName().equals(boxName))
                box = b;
        return box;
    }

    public ArrayList<ComicBox> getBoxesComicIsIn(Comic comic)
    {
        ArrayList<ComicBox> boxesComicIsIn = new ArrayList<>();
        for(ComicBox box : comicBoxes)
            if(comicIsInBox(comic, box.getBoxName()))
                boxesComicIsIn.add(box);
        return boxesComicIsIn;
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
