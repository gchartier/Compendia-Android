package com.gabrieldchartier.compendia.models;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Dictionary;
import java.util.UUID;

public class Comic implements Parcelable
{
    private UUID ID;
    private String title;
    private String cover;
    private ArrayList<ComicCreator> creators;
    private String publisherName;
    private UUID publisherID;
    private String imprintName;
    private UUID imprintID;
    private Dictionary characters;
    private Date releaseDate;
    private Currency coverPrice;
    private String description;
    private int pageCount;
    private String arc;
    private UUID arcID;
    private String age;
    private String synopsis;
    private String barcode;
    private int totalWant;
    private int totalFavorited;
    private int totalOwned;
    private int totalRead;
    private float averageRating;
    private int totalRatings;
    private int totalReviews;
    private String seriesName;
    private UUID seriesID;
    private int printing;
    private String variant;
    private String ageRating;
    private boolean isMiniSeries;
    private boolean isReprint;
    private boolean isOneShot;
    private int miniSeriesLimit;
    private int format;
    private int itemNumber;
    private int collectionType;
    private UUID[] otherVersions;
    private float userRating;
    private Date dateCollected;
    private Currency purchasePrice;
    private String boughtAt;
    private String condition;
    private String gradingAgency;
    private float grade;
    private int quantity;
    private String notes;

    public Comic(String ID, String title, String cover)
    {
        this.ID = UUID.fromString(ID);
        this.title = title;
        this.cover = cover;
    }

    public Comic()
    {
        //assign new stuff;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public UUID getID()
    {
        return ID;
    }

    public void setID(UUID ID)
    {
        this.ID = ID;
    }

    public ArrayList<ComicCreator> getCreators()
    {
        return creators;
    }

    public void setCreators(ArrayList<ComicCreator> creators)
    {
        this.creators = creators;
    }

    public String getPublisherName()
    {
        return publisherName;
    }

    public void setPublisherName(String publisherName)
    {
        this.publisherName = publisherName;
    }

    public UUID getPublisherID()
    {
        return publisherID;
    }

    public void setPublisherID(UUID publisherID)
    {
        this.publisherID = publisherID;
    }

    public String getImprintName()
    {
        return imprintName;
    }

    public void setImprintName(String imprintName)
    {
        this.imprintName = imprintName;
    }

    public UUID getImprintID()
    {
        return imprintID;
    }

    public void setImprintID(UUID imprintID)
    {
        this.imprintID = imprintID;
    }

    public Dictionary getCharacters()
    {
        return characters;
    }

    public void setCharacters(Dictionary characters)
    {
        this.characters = characters;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public Currency getCoverPrice()
    {
        return coverPrice;
    }

    public void setCoverPrice(Currency coverPrice)
    {
        this.coverPrice = coverPrice;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public String getArc()
    {
        return arc;
    }

    public void setArc(String arc)
    {
        this.arc = arc;
    }

    public UUID getArcID()
    {
        return arcID;
    }

    public void setArcID(UUID arcID)
    {
        this.arcID = arcID;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getSynopsis()
    {
        return synopsis;
    }

    public void setSynopsis(String synopsis)
    {
        this.synopsis = synopsis;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    public int getTotalWant()
    {
        return totalWant;
    }

    public void setTotalWant(int totalWant)
    {
        this.totalWant = totalWant;
    }

    public int getTotalFavorited()
    {
        return totalFavorited;
    }

    public void setTotalFavorited(int totalFavorited)
    {
        this.totalFavorited = totalFavorited;
    }

    public int getTotalOwned()
    {
        return totalOwned;
    }

    public void setTotalOwned(int totalOwned)
    {
        this.totalOwned = totalOwned;
    }

    public int getTotalRead()
    {
        return totalRead;
    }

    public void setTotalRead(int totalRead)
    {
        this.totalRead = totalRead;
    }

    public float getAverageRating()
    {
        return averageRating;
    }

    public String getAverageRatingToString()
    {
        return Float.toString(averageRating);
    }

    public void setAverageRating(float averageRating)
    {
        this.averageRating = averageRating;
    }

    public int getTotalRatings()
    {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings)
    {
        this.totalRatings = totalRatings;
    }

    public int getTotalReviews()
    {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews)
    {
        this.totalReviews = totalReviews;
    }

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public UUID getSeriesID()
    {
        return seriesID;
    }

    public void setSeriesID(UUID seriesID)
    {
        this.seriesID = seriesID;
    }

    public int getPrinting()
    {
        return printing;
    }

    public void setPrinting(int printing)
    {
        this.printing = printing;
    }

    public String getVariant()
    {
        return variant;
    }

    public void setVariant(String variant)
    {
        this.variant = variant;
    }

    public String getAgeRating()
    {
        return ageRating;
    }

    public void setAgeRating(String ageRating)
    {
        this.ageRating = ageRating;
    }

    public boolean isMiniSeries()
    {
        return isMiniSeries;
    }

    public void setMiniSeries(boolean miniSeries)
    {
        isMiniSeries = miniSeries;
    }

    public boolean isReprint()
    {
        return isReprint;
    }

    public void setReprint(boolean reprint)
    {
        isReprint = reprint;
    }

    public boolean isOneShot()
    {
        return isOneShot;
    }

    public void setOneShot(boolean oneShot)
    {
        isOneShot = oneShot;
    }

    public int getMiniSeriesLimit()
    {
        return miniSeriesLimit;
    }

    public void setMiniSeriesLimit(int miniSeriesLimit)
    {
        this.miniSeriesLimit = miniSeriesLimit;
    }

    public int getFormat()
    {
        return format;
    }

    public void setFormat(int format)
    {
        this.format = format;
    }

    public int getItemNumber()
    {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber)
    {
        this.itemNumber = itemNumber;
    }

    public int getCollectionType()
    {
        return collectionType;
    }

    public void setCollectionType(int collectionType)
    {
        this.collectionType = collectionType;
    }

    public UUID[] getOtherVersions()
    {
        return otherVersions;
    }

    public void setOtherVersions(UUID[] otherVersions)
    {
        this.otherVersions = otherVersions;
    }

    public boolean isRated()
    {
        return userRating >= 0.0;
    }

    public float getUserRating()
    {
        return userRating;
    }

    public void setUserRating(float userRating)
    {
        this.userRating = userRating;
    }

    public Date getDateCollected()
    {
        return dateCollected;
    }

    public void setDateCollected(Date dateCollected)
    {
        this.dateCollected = dateCollected;
    }

    public Currency getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(Currency purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public String getBoughtAt()
    {
        return boughtAt;
    }

    public void setBoughtAt(String boughtAt)
    {
        this.boughtAt = boughtAt;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    public String getGradingAgency()
    {
        return gradingAgency;
    }

    public void setGradingAgency(String gradingAgency)
    {
        this.gradingAgency = gradingAgency;
    }

    public float getGrade()
    {
        return grade;
    }

    public void setGrade(float grade)
    {
        this.grade = grade;
    }

    public String getGradeAndAgencyString()
    {
        return gradingAgency + " " + grade;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public static Creator<Comic> getCREATOR()
    {
        return CREATOR;
    }

    protected Comic(Parcel in)
    {
        ID =  (UUID) in.readSerializable();
        title = in.readString();
        cover = in.readString();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>()
    {
        @Override
        public Comic createFromParcel(Parcel in)
        {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size)
        {
            return new Comic[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeSerializable(ID);
        dest.writeString(title);
        dest.writeString(cover);
    }
}
