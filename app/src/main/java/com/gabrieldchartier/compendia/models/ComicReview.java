package com.gabrieldchartier.compendia.models;

import java.util.UUID;

public class ComicReview
{
    private UUID ID;
    private UUID comicID;
    private String reviewText;
    private String reviewTitle;
    private boolean containsSpoilers;
    private int votes;

    public ComicReview(UUID ID, UUID comicID, String reviewText, String reviewTitle, boolean containsSpoilers, int votes)
    {
        this.ID = ID;
        this.comicID = comicID;
        this.reviewText = reviewText;
        this.reviewTitle = reviewTitle;
        this.containsSpoilers = containsSpoilers;
        this.votes = votes;
    }

    public UUID getID()
    {
        return ID;
    }

    public void setID(UUID ID)
    {
        this.ID = ID;
    }

    public UUID getComicID()
    {
        return comicID;
    }

    public void setComicID(UUID comicID)
    {
        this.comicID = comicID;
    }

    public String getReviewText()
    {
        return reviewText;
    }

    public void setReviewText(String reviewText)
    {
        this.reviewText = reviewText;
    }

    public String getReviewTitle()
    {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle)
    {
        this.reviewTitle = reviewTitle;
    }

    public boolean containsSpoilers()
    {
        return containsSpoilers;
    }

    public void setContainsSpoilers(boolean containsSpoilers)
    {
        this.containsSpoilers = containsSpoilers;
    }

    public int getVotes()
    {
        return votes;
    }

    public void setVotes(int votes)
    {
        this.votes = votes;
    }
}
