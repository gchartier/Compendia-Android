package com.gabrieldchartier.compendia.models;

import android.support.annotation.Nullable;
import android.util.Log;
import com.gabrieldchartier.compendia.util.TempCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class Collection
{

    // Variables
    private List<Comic> comics;
    private List<ComicBox> comicBoxes;
    private List<ComicReview> reviews;

    //TODO retrieve from repository
    public Collection()
    {
        TempCollection tempCollection = new TempCollection();
        comics = tempCollection.comics;
        comicBoxes = tempCollection.comicBoxes;
        reviews = tempCollection.comicReviews;

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
