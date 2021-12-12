package com.alexsykes.roomwithaviewjava;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexsykes.roomwithaviewjava.Trial;
import com.alexsykes.roomwithaviewjava.TrialRepository;

import java.util.List;

public class TrialViewModel extends AndroidViewModel {

    private TrialRepository mRepository;

    private final LiveData<List<Trial>> mAllTrials;

    public TrialViewModel (Application application) {
        super(application);
        mRepository = new TrialRepository(application);
        mAllTrials = mRepository.getAllTrials();
    }

    LiveData<List<Trial>> getAllTrials() { return mAllTrials; }

    public void insert(Trial trial) { mRepository.insert(trial); }
}