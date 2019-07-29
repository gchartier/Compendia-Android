package com.gabrieldchartier.compendia.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(tableName="comics")
public class Comic implements Parcelable
{
    private static final String MODERN_AGE_COMIC = "Modern Age";
    private static final int SINGLE_ISSUE_FORMAT_CODE = 1;
    private static final String SINGLE_ISSUE_FORMAT_TEXT = "Issue";
    private static final int MATURE_RATING_CODE = 1;
    private static final String MATURE_RATING_TEXT = "MA";

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="comicID")
    private UUID comicID;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="cover")
    private String cover;
    @ColumnInfo(name="creators")
    private List<ComicCreator> creators;
    @ColumnInfo(name="publisherName")
    private String publisherName;
    @ColumnInfo(name="publisherID")
    private UUID publisherID;
    @ColumnInfo(name="imprintName")
    private String imprintName;
    @ColumnInfo(name="imprintID")
    private UUID imprintID;
    @Ignore
    private List<ComicCharacter> characters;
    @ColumnInfo(name="releaseDate")
    private String releaseDate;
    @ColumnInfo(name="coverPrice")
    private String coverPrice;
    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="pageCount")
    private int pageCount;
    @ColumnInfo(name="arc")
    private String arc;
    @ColumnInfo(name="arcID")
    private UUID arcID;
    @ColumnInfo(name="age")
    private int age;
    @ColumnInfo(name="synopsis")
    private String synopsis;
    @ColumnInfo(name="barcode")
    private String barcode;
    @ColumnInfo(name="totalWant")
    private int totalWant;
    @ColumnInfo(name="totalFavorited")
    private int totalFavorited;
    @ColumnInfo(name="totalOwned")
    private int totalOwned;
    @ColumnInfo(name="totalRead")
    private int totalRead;
    @ColumnInfo(name="averageRating")
    private float averageRating;
    @ColumnInfo(name="totalRatings")
    private int totalRatings;
    @ColumnInfo(name="totalReviews")
    private int totalReviews;
    @Ignore
    private List<ComicReview> reviews;//TODO
    @ColumnInfo(name="seriesName")
    private String seriesName;
    @ColumnInfo(name="seriesID")
    private UUID seriesID;
    @ColumnInfo(name="printing")
    private int printing;
    @ColumnInfo(name="isVariant")
    private boolean isVariant;
    @ColumnInfo(name="variant")
    private String variant;
    @ColumnInfo(name="ageRating")
    private String ageRating;
    @ColumnInfo(name="isMiniSeries")
    private boolean isMiniSeries;
    @ColumnInfo(name="isReprint")
    private boolean isReprint;
    @ColumnInfo(name="isOneShot")
    private boolean isOneShot;
    @ColumnInfo(name="miniSeriesLimit")
    private int miniSeriesLimit;
    @ColumnInfo(name="format")
    private String format;
    @ColumnInfo(name="itemNumber")
    private int itemNumber;
    @ColumnInfo(name="collectionType")
    private int collectionType;
    @ColumnInfo(name="otherVersions")
    private List<UUID> otherVersions;
    @ColumnInfo(name="userRating")
    private float userRating;
    @ColumnInfo(name="dateCollected")
    private String dateCollected;
    @ColumnInfo(name="purchasePrice")
    private String purchasePrice;
    @ColumnInfo(name="boughtAt")
    private String boughtAt;
    @ColumnInfo(name="condition")
    private String condition;
    @ColumnInfo(name="gradingAgency")
    private String gradingAgency;
    @ColumnInfo(name="grade")
    private float grade;
    @ColumnInfo(name="quantity")
    private int quantity;
    @ColumnInfo(name="notes")
    private String notes;

    public Comic(@NotNull UUID comicID, String title, String cover, List<ComicCreator> creators, String publisherName,
                 UUID publisherID, String imprintName, UUID imprintID, List<ComicCharacter> characters,
                 String releaseDate, String coverPrice, String description, int pageCount, String arc,
                 UUID arcID, int age, String synopsis, String barcode, int totalWant, int totalFavorited,
                 int totalOwned, int totalRead, float averageRating, int totalRatings, int totalReviews,
                 List<ComicReview> reviews, String seriesName, UUID seriesID, int printing,
                 String variant, String ageRating, boolean isMiniSeries, boolean isReprint, boolean isOneShot, int miniSeriesLimit,
                 String format, int itemNumber, int collectionType, List<UUID> otherVersions,
                 float userRating, String dateCollected, String purchasePrice, String boughtAt, String condition,
                 String gradingAgency, float grade, int quantity, String notes, boolean isVariant)
    {
        this.comicID = comicID;
        this.title = title;
        this.cover = cover;
        this.creators = new ArrayList<ComicCreator>(creators);
        this.publisherName = publisherName;
        this.publisherID = publisherID;
        this.imprintName = imprintName;
        this.imprintID = imprintID;
        this.characters = characters;
        this.releaseDate = releaseDate;
        this.coverPrice = coverPrice;
        this.description = description;
        this.pageCount = pageCount;
        this.arc = arc;
        this.arcID = arcID;
        this.age = age;
        this.synopsis = synopsis;
        this.barcode = barcode;
        this.totalWant = totalWant;
        this.totalFavorited = totalFavorited;
        this.totalOwned = totalOwned;
        this.totalRead = totalRead;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
        this.totalReviews = totalReviews;
        this.reviews = reviews;
        this.seriesName = seriesName;
        this.seriesID = seriesID;
        this.printing = printing;
        this.isVariant = isVariant;
        this.variant = variant;
        this.ageRating = ageRating;
        this.isMiniSeries = isMiniSeries;
        this.isReprint = isReprint;
        this.isOneShot = isOneShot;
        this.miniSeriesLimit = miniSeriesLimit;
        this.format = format;
        this.itemNumber = itemNumber;
        this.collectionType = collectionType;
        this.otherVersions = otherVersions;
        this.userRating = userRating;
        this.dateCollected = dateCollected;
        this.purchasePrice = purchasePrice;
        this.boughtAt = boughtAt;
        this.condition = condition;
        this.gradingAgency = gradingAgency;
        this.grade = grade;
        this.quantity = quantity;
        this.notes = notes;
    }

    public Comic(@NotNull UUID comicID, String title, String cover, String publisherName, UUID publisherID,
                 String imprintName, UUID imprintID, String releaseDate, String coverPrice,
                 String description, int pageCount, String arc, UUID arcID, int age, String synopsis,
                 String barcode, int totalWant, int totalFavorited, int totalOwned, int totalRead,
                 float averageRating, int totalRatings, int totalReviews, String seriesName,
                 UUID seriesID, int printing, boolean isVariant, String variant, String ageRating,
                 boolean isMiniSeries, boolean isReprint, boolean isOneShot, int miniSeriesLimit,
                 String format, int itemNumber, int collectionType, List<UUID> otherVersions,
                 float userRating, String dateCollected, String purchasePrice, String boughtAt,
                 String condition, String gradingAgency, float grade, int quantity, String notes)
    {
        this.comicID = comicID;
        this.title = title;
        this.cover = cover;
        this.publisherName = publisherName;
        this.publisherID = publisherID;
        this.imprintName = imprintName;
        this.imprintID = imprintID;
        this.releaseDate = releaseDate;
        this.coverPrice = coverPrice;
        this.description = description;
        this.pageCount = pageCount;
        this.arc = arc;
        this.arcID = arcID;
        this.age = age;
        this.synopsis = synopsis;
        this.barcode = barcode;
        this.totalWant = totalWant;
        this.totalFavorited = totalFavorited;
        this.totalOwned = totalOwned;
        this.totalRead = totalRead;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
        this.totalReviews = totalReviews;
        this.seriesName = seriesName;
        this.seriesID = seriesID;
        this.printing = printing;
        this.isVariant = isVariant;
        this.variant = variant;
        this.ageRating = ageRating;
        this.isMiniSeries = isMiniSeries;
        this.isReprint = isReprint;
        this.isOneShot = isOneShot;
        this.miniSeriesLimit = miniSeriesLimit;
        this.format = format;
        this.itemNumber = itemNumber;
        this.collectionType = collectionType;
        this.otherVersions = otherVersions;
        this.userRating = userRating;
        this.dateCollected = dateCollected;
        this.purchasePrice = purchasePrice;
        this.boughtAt = boughtAt;
        this.condition = condition;
        this.gradingAgency = gradingAgency;
        this.grade = grade;
        this.quantity = quantity;
        this.notes = notes;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    @NotNull
    public UUID getComicID()
    {
        return comicID;
    }

    public void setComicID(@NotNull UUID comicID)
    {
        this.comicID = comicID;
    }

    public List<ComicCreator> getCreators()
    {
        return creators;
    }

    public void setCreators(List<ComicCreator> creators)
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

    public List<ComicCharacter> getCharacters()
    {
        return characters;
    }

    public void setCharacters(List<ComicCharacter> characters)
    {
        this.characters = characters;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getCoverPrice()
    {
        return coverPrice;
    }

    public void setCoverPrice(String coverPrice)
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

    //TODO
    public String getAgeAsString()
    {
        if(age == 4)
            return MODERN_AGE_COMIC;
        else
            return "Not Modern";
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
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

    public boolean isVariant()
    {
        return isVariant;
    }

    public void setVariant(boolean variant)
    {
        isVariant = variant;
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

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
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

    public List<UUID> getOtherVersions()
    {
        return otherVersions;
    }

    public String[] getOtherVersionsAsStringArray()
    {
        String[] otherVersionIDs = new String[otherVersions.size()];
        for(int i = 0; i < otherVersions.size(); i++)
        {
            otherVersionIDs[i] = otherVersions.get(i).toString();
        }
        return otherVersionIDs;
    }

    public void setOtherVersions(List<UUID> otherVersions)
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

    public String getDateCollected()
    {
        return dateCollected;
    }

    public void setDateCollected(String dateCollected)
    {
        this.dateCollected = dateCollected;
    }

    public String getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice)
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
        comicID =  (UUID) in.readSerializable();
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
        dest.writeSerializable(comicID);
        dest.writeString(title);
        dest.writeString(cover);
    }
}
