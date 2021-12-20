package com.alexsykes.trialmonsterclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultListPortraitAdapter extends RecyclerView.Adapter<ResultListPortraitAdapter.ResultViewHolder> {

    ArrayList<HashMap<String, String>> theResultList;
    HashMap<String, String> theResult;
    OnItemClickListener listener;

    public ResultListPortraitAdapter(ArrayList<HashMap<String, String>> theResultList) {
        this.theResultList = theResultList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Point to data holder layout
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_item, viewGroup, false);
        ResultViewHolder resultViewHolder = new ResultViewHolder(v);
        Log.i("Info", "onCreateViewHolder: ");
        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(final ResultViewHolder resultViewHolder, int i) {
        theResult = theResultList.get(i);
        int numlaps = ResultListActivity.numlaps;
        int visibility;

        // Pass index value to listener method
        resultViewHolder.itemView.setTag(i);
        resultViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible = resultViewHolder.detailRow.getVisibility();
                int pointer = (int) v.getTag();
                int newVisibility;

                if (visible == View.GONE) {
                    newVisibility = View.VISIBLE;
                } else {
                    newVisibility = View.GONE;
                }
                resultViewHolder.detailRow.setVisibility(newVisibility);
                theResultList.get(pointer).put("visibility", Integer.toString(newVisibility));
            }
        });
        //

        int backgroundColor = ContextCompat.getColor(resultViewHolder.itemView.getContext(), R.color.offWhite);
        int white = ContextCompat.getColor(resultViewHolder.itemView.getContext(), R.color.colorWhite);

        // Insert * character after each section
        // Create sectionsScore data string

        String theSectionScores, theSections, separator;
        separator = " | ";
        theSectionScores = theResult.get("sectionscores");
        theSections = "";
        char theChar;

        for (int pos = 0; pos < theSectionScores.length(); pos++) {
            theChar = theSectionScores.charAt(pos);
            if (pos % numlaps == 0) {
                theSections = theSections + separator;
            }
            theSections = theSections + theChar;
        }
        theSections = (theSections + separator).trim();
        // End

        // Format background
        if (i % 2 != 0) {
            resultViewHolder.itemView.setBackgroundColor(backgroundColor);
        } else {
            resultViewHolder.itemView.setBackgroundColor(white);
        }

        if (theResult.get("position").equals("1")) {
            resultViewHolder.topRow.setVisibility(View.VISIBLE);

        } else {
            resultViewHolder.topRow.setVisibility(View.GONE);
        }

        String theClass = theResult.get("class");
        if (theClass.contains("Adult")) {
            theClass = "";
        }

        resultViewHolder.detailRow.setVisibility(View.GONE);
        resultViewHolder.positionTextView.setText(theResult.get("position"));
        resultViewHolder.riderTextView.setText(theResult.get("rider"));
        resultViewHolder.nameTextView.setText(theResult.get("name"));
        resultViewHolder.machineTextView.setText(theResult.get("machine"));
        resultViewHolder.totalTextView.setText(theResult.get("total"));
        resultViewHolder.summaryTotalTextView.setText(theResult.get("total"));
        resultViewHolder.classTextView.setText(theClass);
        resultViewHolder.cleansTextView.setText(theResult.get("cleans"));
        resultViewHolder.onesTextView.setText(theResult.get("ones"));
        resultViewHolder.twosTextView.setText(theResult.get("twos"));
        resultViewHolder.threesTextView.setText(theResult.get("threes"));
        resultViewHolder.fivesTextView.setText(theResult.get("fives"));
        resultViewHolder.missedTextView.setText(theResult.get("missed"));
        resultViewHolder.sectionScoreTextView.setText(theSections);

        // Check for missed sections
        // Display position if dnf = 0
        resultViewHolder.courseTextView.setText(theResult.get("course"));

        if (theResult.get("dnf").equals("1")) {
            resultViewHolder.positionTextView.setText("");
            resultViewHolder.totalTextView.setText("DNF");
        }
    }

    @Override
    public int getItemCount() {
        return theResultList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(HashMap<String, String> theResult);
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView positionTextView, riderTextView, machineTextView, totalTextView, nameTextView,
                summaryTotalTextView, cleansTextView, onesTextView, twosTextView, threesTextView, fivesTextView, missedTextView,
                courseTextView, classTextView, scoresTextView, sectionScoreTextView;
        LinearLayout topRow, detailRow, dataRow;


        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTextView = itemView.findViewById(R.id.courseTextView);
            topRow = itemView.findViewById(R.id.topRow);
            detailRow = itemView.findViewById(R.id.detailRow);

            positionTextView = itemView.findViewById(R.id.positionTextView);
            riderTextView = itemView.findViewById(R.id.riderTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            machineTextView = itemView.findViewById(R.id.machineTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            summaryTotalTextView = itemView.findViewById(R.id.summaryTotalTextView);
            cleansTextView = itemView.findViewById(R.id.cleansTextView);
            onesTextView = itemView.findViewById(R.id.onesTextView);
            twosTextView = itemView.findViewById(R.id.twosTextView);
            threesTextView = itemView.findViewById(R.id.threesTextView);
            fivesTextView = itemView.findViewById(R.id.fivesTextView);
            missedTextView = itemView.findViewById(R.id.missedTextView);
            classTextView = itemView.findViewById(R.id.classTextView);
            scoresTextView = itemView.findViewById(R.id.scoresTextView);
            sectionScoreTextView = itemView.findViewById(R.id.sectionScoreTextView);
        }

        public void bind(final HashMap<String, String> theScore, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = theScore.get(("id"));
                    Context context = v.getContext();
                }
            });

        }
    }
}
