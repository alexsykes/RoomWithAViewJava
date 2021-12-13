package com.alexsykes.trialmonsterclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrialViewHolder extends RecyclerView.ViewHolder {
    public final TextView nameTextView, dateTextView, clubTextView, locationTextView;

    public TrialViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        clubTextView = itemView.findViewById(R.id.clubTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        locationTextView = itemView.findViewById(R.id.locationTextView);
    }

    public void bind(Trial trial, final TrialListAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(v -> {
            String id = String.valueOf(trial.id);
            Context context = v.getContext();
            ((MainActivity) context).onClickCalled(id);

        });
        nameTextView.setText(trial.name);
        clubTextView.setText(trial.club);
        dateTextView.setText(trial.date);
        locationTextView.setText(trial.location);
    }

    static TrialViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trial_item, parent, false);
        return new TrialViewHolder(view);
    }
}
