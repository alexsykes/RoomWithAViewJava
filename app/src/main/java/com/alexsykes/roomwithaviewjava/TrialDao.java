package com.alexsykes.roomwithaviewjava;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrialDao {
    @Query(("SELECT * FROM trials"))
    LiveData<List<Trial>> getAllTrials();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Trial  trial);

    @Query("DELETE FROM trials")
    void deleteAll();
}
