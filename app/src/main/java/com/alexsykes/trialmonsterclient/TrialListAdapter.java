package com.alexsykes.trialmonsterclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        int backgroundColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.offWhite);
        int white = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorWhite);

        String theDate = current.date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("MMM d, yyyy");
        theDate = targetFormat.format(sourceDate);

        holder.nameTextView.setText(current.name);
        holder.clubTextView.setText(current.club);
        holder.locationTextView.setText(current.location);
        holder.dateTextView.setText(theDate);
        holder.bind(current, listener);

        if (position % 2 != 0) {
            holder.itemView.setBackgroundColor(backgroundColor);
        } else {
            holder.itemView.setBackgroundColor(white);
        }
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
