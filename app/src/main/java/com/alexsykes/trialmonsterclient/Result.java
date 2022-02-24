package com.alexsykes.trialmonsterclient;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "result_table")
public class Result {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;
    int trialid;
    int number;
    String course;
    String name;
    String classs;
    String machine;
    String total;
    String cleans;
    String ones;
    String twos;
    String threes;
    String fives;
    String missed;
    int dnf;
    String sectionscores;
    String scores;
    boolean showTopRow;

    public Result(int trialid, int id, int number, String course, String name, String classs, String machine, String total, String cleans, String ones, String twos, String threes, String fives, String missed, int dnf, String sectionscores, String scores) {
        this.trialid = trialid;
        this.id = id;
        this.number = number;
        this.course = course;
        this.name = name;
        this.classs = classs;
        this.machine = machine;
        this.total = total;
        this.cleans = cleans;
        this.ones = ones;
        this.twos = twos;
        this.threes = threes;
        this.fives = fives;
        this.missed = missed;
        this.dnf = dnf;
        this.sectionscores = sectionscores;
        this.scores = scores;
        this.showTopRow = false;
    }
}
