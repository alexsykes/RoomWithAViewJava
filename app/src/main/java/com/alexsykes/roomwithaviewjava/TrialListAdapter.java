package com.alexsykes.roomwithaviewjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class TrialListAdapter extends ListAdapter<Trial, TrialViewHolder> {

    OnItemClickListener listener;

    @NonNull
    @Override
    public TrialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial_item, parent, false);
        TrialViewHolder trialViewHolder = new TrialViewHolder(v);
        // return trialViewHolder;
        return TrialViewHolder.create(parent);
    }

    public TrialListAdapter(@NonNull DiffUtil.ItemCallback<Trial> diffCallback) {
        super(diffCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull TrialViewHolder holder, int position) {
        Trial current = getItem(position);
        holder.nameTextView.setText(current.name);
        holder.clubTextView.setText(current.club);
        holder.locationTextView.setText(current.location);
        holder.dateTextView.setText(current.date);
        holder.bind(current, listener);
    }

    public interface OnItemClickListener {
        void onItemClick(Trial theTrial);
    }

    static class TrialDiff extends DiffUtil.ItemCallback<Trial> {

        @Override
        public boolean areItemsTheSame(@NonNull Trial oldItem, @NonNull Trial newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Trial oldItem, @NonNull Trial newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }
}
