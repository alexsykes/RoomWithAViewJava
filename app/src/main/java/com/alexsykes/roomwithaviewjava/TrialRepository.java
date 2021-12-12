package com.alexsykes.roomwithaviewjava;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TrialRepository {
    private TrialDao trialDao;
    private LiveData<List<Trial>> allTrials;

    public TrialRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        trialDao = db.trialDao();
        allTrials = trialDao.getAllTrials();
    }

   public LiveData<List<Trial>> getAllTrials() { return allTrials; }

   public void insert(Trial trial) {
        WordRoomDatabase.databaseWriteExecutor.execute(() ->{
            trialDao.insert(trial);
        });
    }
}
