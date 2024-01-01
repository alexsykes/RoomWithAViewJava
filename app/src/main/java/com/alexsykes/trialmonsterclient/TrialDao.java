package com.alexsykes.trialmonsterclient;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trial  trial);

    @Query("DELETE FROM trial_table")
    void deleteAll();

    @Query("SELECT * FROM trial_table ORDER BY date DESC")
    LiveData<List<Trial>> getAllTrials();
}
