package com.alexsykes.trialmonsterclient;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class FutureTrialListAdapter extends RecyclerView.Adapter<FutureTrialListAdapter.FutureTrialViewHolder> {
    ArrayList<HashMap<String, String>> theTrialList;
    HashMap<String, String> theTrial;
    OnItemClickListener listener;

    public FutureTrialListAdapter(ArrayList<HashMap<String, String>> theTrialList) {
        this.theTrialList = theTrialList;
    }


    public FutureTrialListAdapter(ArrayList<HashMap<String, String>> theTrialList, OnItemClickListener listener) {
        this.theTrialList = theTrialList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FutureTrialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FutureTrialViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(HashMap<String, String> theTrial);
    }

    public class FutureTrialViewHolder extends RecyclerView.ViewHolder {
        public FutureTrialViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
