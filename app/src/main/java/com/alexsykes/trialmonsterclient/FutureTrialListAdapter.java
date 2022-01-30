package com.alexsykes.trialmonsterclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FutureTrialListAdapter extends RecyclerView.Adapter<FutureTrialListAdapter.FutureTrialViewHolder> {
    ArrayList<HashMap<String, String>> theTrialList;
    HashMap<String, String> theTrial;
    OnItemClickListener listener;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public FutureTrialListAdapter(ArrayList<HashMap<String, String>> theTrialList) {
        this.theTrialList = theTrialList;
    }


    public FutureTrialListAdapter(ArrayList<HashMap<String, String>> theTrialList, OnItemClickListener listener) {
        this.theTrialList = theTrialList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FutureTrialViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Point to data holder layout
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trial_item, viewGroup, false);
        FutureTrialViewHolder holder = new FutureTrialViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FutureTrialViewHolder holder, int i) {
        // Populate TextViews with data
        theTrial = theTrialList.get(i);

        String theDate = theTrial.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int backgroundColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.offWhite);
        int white = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorWhite);
        SimpleDateFormat targetFormat = new SimpleDateFormat("MMM d, yyyy");
        theDate = targetFormat.format(sourceDate);

        holder.nameTextView.setText(theTrial.get("name"));
        holder.clubTextView.setText(theTrial.get("club"));
        holder.venueTextView.setText(theTrial.get("venue_name"));
        holder.dateTextView.setText(theDate);
        holder.bind(theTrial, listener);


        if (i % 2 != 0) {
            holder.itemView.setBackgroundColor(backgroundColor);
        } else {
            holder.itemView.setBackgroundColor(white);
        }
    }

    @Override
    public int getItemCount() {
        return theTrialList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(HashMap<String, String> theTrial);
    }

    public static class FutureTrialViewHolder extends RecyclerView.ViewHolder {

        TextView clubTextView;
        TextView dateTextView;
        TextView nameTextView;
        TextView venueTextView;

        public FutureTrialViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            clubTextView = itemView.findViewById(R.id.clubTextView);
            venueTextView = itemView.findViewById(R.id.locationTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }


        public void bind(final HashMap<String, String> theTrial, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = theTrial.get("id");
                    Context context = v.getContext();
                    ((FutureTrialListActivity) context).onClickCalled(id);
                }
            });
        }
    }
}
