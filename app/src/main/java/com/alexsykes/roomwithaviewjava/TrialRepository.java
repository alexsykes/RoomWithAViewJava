package com.alexsykes.roomwithaviewjava;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TrialRepository {
    private TrialDao trialDao;
    private LiveData<List<Trial>> allTrials;

    public TrialRepository(Application application) {
        TrialRoomDatabase db = TrialRoomDatabase.getDatabase(application);
        trialDao = db.trialDao();
        allTrials = trialDao.getAllTrials();
    }

   public LiveData<List<Trial>> getAllTrials() { return allTrials; }

   public void insert(Trial trial) {
        TrialRoomDatabase.databaseWriteExecutor.execute(() ->{
            trialDao.insert(trial);
        });
    }
}
