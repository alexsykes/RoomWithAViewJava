package com.alexsykes.trialmonsterclient;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewTrialActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.alexsykes.roomwithaviewjava.wordlistsql.REPLY";

    private EditText editTrialName;
    private EditText editTrialClub;
    private EditText editTrialDate;
    private EditText editTrialLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trial);

        editTrialName = findViewById(R.id.edit_trial_name);
        editTrialClub = findViewById(R.id.edit_trial_club);
        editTrialDate = findViewById(R.id.edit_trial_date);
        editTrialLocation = findViewById(R.id.edit_trial_location);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editTrialName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String name = editTrialName.getText().toString();
                String club = editTrialClub.getText().toString();
                String date = editTrialDate.getText().toString();
                String location = editTrialLocation.getText().toString();
                replyIntent.putExtra("name", name);
                replyIntent.putExtra("club", club);
                replyIntent.putExtra("date", date);
                replyIntent.putExtra("location", location);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}