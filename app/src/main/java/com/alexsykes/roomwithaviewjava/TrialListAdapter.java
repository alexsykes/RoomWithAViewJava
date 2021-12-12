package com.alexsykes.roomwithaviewjava;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class TrialListAdapter extends ListAdapter<Trial, TrialViewHolder> {
    
    public TrialListAdapter(@NonNull DiffUtil.ItemCallback<Trial> diffCallback) {
        super(diffCallback);
    }
    
    @Override
    public TrialViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return TrialViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TrialViewHolder holder, int position) {
        Trial current = getItem(position);
        holder.bind(current.name);
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
