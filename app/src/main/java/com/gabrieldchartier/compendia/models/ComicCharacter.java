package com.gabrieldchartier.compendia.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

@Entity(tableName="comic_characters")
public class ComicCharacter
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="ID")
    private UUID ID;
    @ColumnInfo(name="name")
    private String name;

    public ComicCharacter(@NotNull UUID ID, String name)
    {
        this.ID = ID;
        this.name = name;
    }

    @NonNull
    public UUID getID()
    {
        return ID;
    }


    public void setID(@NotNull UUID ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
