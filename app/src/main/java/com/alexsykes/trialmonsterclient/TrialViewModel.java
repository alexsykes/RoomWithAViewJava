package com.alexsykes.trialmonsterclient;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TrialViewModel extends AndroidViewModel {

    private final TrialRepository mRepository;

    private final LiveData<List<Trial>> mAllTrials;

    public TrialViewModel (Application application) {
        super(application);
        mRepository = new TrialRepository(application);
        mAllTrials = mRepository.getAllTrials();
    }


    LiveData<List<Trial>> getAllTrials() { return mAllTrials; }

    public void insert(Trial trial) { mRepository.insert(trial); }
}