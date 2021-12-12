package com.alexsykes.roomwithaviewjava;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "trials")
public class Trial {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    int id;
    String name;
    String club;
    String date;

    public Trial(@NonNull String name) {
        this.name = name;
    }
//    @Ignore
//    public Trial(int id, String name, String club, String date) {
//        this.id = id;
//        this.name = name;
//        this.club = club;
//        this.date = date;
//    }
//
//    @Ignore
//    public Trial(String name, String club, String date) {
//        this.name = name;
//        this.club = club;
//        this.date = date;
//    }

    public Trial getTrial() {
        return new Trial("Dummy");
    }
}
