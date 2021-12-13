package com.alexsykes.trialmonsterclient;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trial_table")
public class Trial {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;
    @ColumnInfo(name = "name")
    public String name;
    String club;
    String date;
    String location;

    //
//    public Trial(int id, String name, String club, String date) {
//        this.id = id;
//        this.name = name;
//        this.club = club;
//        this.date = date;
//    }
//
//
//    public Trial(String name, String club, String date, String location) {
//        this.name = name;
//        this.club = club;
//        this.date = date;
//        this.location = location;
//    }
    public Trial(int id, String name, String club, String date, String location) {
        this.id = id;
        this.name = name;
        this.club = club;
        this.date = date;
        this.location = location;
    }

    public String getTrial() {
        return this.name;
    }
}
