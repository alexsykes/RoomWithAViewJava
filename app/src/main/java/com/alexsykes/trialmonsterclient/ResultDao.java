package com.alexsykes.trialmonsterclient;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Result result);

    @Query("DELETE FROM result_table")
    void deleteAll();

    @Query("SELECT * FROM result_table ORDER BY id DESC")
    LiveData<List<Result>> getAllTrials();
}
