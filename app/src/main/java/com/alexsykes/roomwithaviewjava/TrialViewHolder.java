package com.alexsykes.roomwithaviewjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrialViewHolder extends RecyclerView.ViewHolder {
    private  final TextView nameTextView;
    private  final TextView clubTextView;
    private  final TextView dateTextView;

    public TrialViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        clubTextView = itemView.findViewById(R.id.clubTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
    }

    public void bind(String text) {
        nameTextView.setText(text);
    }

    static TrialViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trial_item, parent, false);
        return new TrialViewHolder(view);
    }
}
