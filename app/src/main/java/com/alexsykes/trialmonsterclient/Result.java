package com.alexsykes.trialmonsterclient;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "result_table")
public class Result {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

}
