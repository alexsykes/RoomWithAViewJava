
package com.alexsykes.trialmonsterclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

public class EntryActivity extends AppCompatActivity {
    TextInputLayout firstNameTextInput, lastnameTextInput, emailTextInput, enteredByTextInput, acuTextInput;
    // TextInputEditText firstnameEditText, lastnameEditText;
    RadioGroup modeGroup;
    RadioButton myselfRadioButton, otherRadioButton;
    int mode;
    int selectedCourseID, selectedClassID;
    MaterialButtonToggleGroup courseGroup, classGroup;
    String courses, classes, courseSelected, classSelected;
    String[] courselist, classlist;

    String firstname, lastname, enteredBy, acu;
    SharedPreferences defaults;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        courses = getIntent().getStringExtra("courses");
        classes = getIntent().getStringExtra("classes");
        courselist = courses.split(",");
        classlist = classes.split(",");
        defaults = this.getPreferences(Context.MODE_PRIVATE);
        setUpUI();
        setupInitialValues();
        MaterialButton classSelectedButton = findViewById(selectedClassID);
        MaterialButton courseSelectedButton = findViewById(selectedCourseID);
//        classSelectedButton.setChecked(true);
//        courseSelectedButton.setChecked(true);
        addListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Info", "onStop: ");
        editor = defaults.edit();
        editor.putString("firstname", firstNameTextInput.getEditText().getText().toString());
        editor.putString("enteredBy", enteredByTextInput.getEditText().getText().toString());
        editor.putString("lastname", lastnameTextInput.getEditText().getText().toString());
        editor.putString("email", emailTextInput.getEditText().getText().toString());
        editor.putString("acu", acuTextInput.getEditText().getText().toString());
        editor.apply();
    }

    // MARK: Utility methods
    private void setupInitialValues() {
        modeGroup.check(R.id.myselfRadioButton);
        enteredByTextInput.setVisibility(View.GONE);
        defaults = this.getPreferences(Context.MODE_PRIVATE);
        mode = defaults.getInt("mode", 0);
        if (mode == 0) {
            enteredByTextInput.setVisibility(View.GONE);
            modeGroup.check(R.id.myselfRadioButton);
        } else if (mode == 1) {
            enteredByTextInput.setVisibility(View.VISIBLE);
            modeGroup.check(R.id.otherRadioButton);
        }

        enteredByTextInput.getEditText().setText(defaults.getString("enteredBy", ""));
        firstNameTextInput.getEditText().setText(defaults.getString("firstname", ""));
        lastnameTextInput.getEditText().setText(defaults.getString("lastname", ""));
        emailTextInput.getEditText().setText(defaults.getString("email", ""));
        enteredByTextInput.getEditText().setText(defaults.getString("enteredBy", ""));
        acuTextInput.getEditText().setText(defaults.getString("acu", ""));
        courseSelected = defaults.getString("courseSelected", "");
        classSelected = defaults.getString("classSelected", "");
        selectedClassID = defaults.getInt("selectedClassID", -1);
        selectedCourseID = defaults.getInt("selectedCourseID", -1);

        //       MaterialButton classActive = findViewById(classSelected);
        //       classActive.setChecked(true);
        //       MaterialButton courseActive = findViewById(courseSelected);
        //       courseActive.setChecked(true);
    }

    private void setUpUI() {
        firstNameTextInput = findViewById(R.id.firstnameTextInput);
        lastnameTextInput = findViewById(R.id.lastnameTextInput);
        emailTextInput = findViewById(R.id.emailTextInput);
        enteredByTextInput = findViewById(R.id.enteredByTextInput);
        acuTextInput = findViewById(R.id.acuTextInput);

        modeGroup = findViewById(R.id.modeGroup);
        myselfRadioButton = findViewById(R.id.myselfRadioButton);
        otherRadioButton = findViewById(R.id.otherRadioButton);

        classGroup = findViewById(R.id.classGroup);
        classGroup.setSelectionRequired(true);
        classGroup.removeAllViews();

        courseGroup = findViewById(R.id.courseGroup);
        courseGroup.setSelectionRequired(true);
        courseGroup.removeAllViews();

        MaterialButton button;
        int style = R.attr.materialButtonOutlinedStyle;
        // style = R.style.CustomButtonStyle;
        for (int i = 0; i < courselist.length; i++) {
            button = new MaterialButton(this, null, style);
            button.setTag(i);
            button.setText(courselist[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = defaults.edit();
                    String selected = String.valueOf(v.getTag());
                    int id = v.getId();
                    editor.putString("selectedCourse", selected);
                    editor.putInt("selectedCourseID", id);
                    editor.apply();
                    //   Log.i("Info", "Course: " + selected);
                }
            });
            courseGroup.addView(button);
        }
        // style = R.style.CustomButtonStyle;
        for (int i = 0; i < classlist.length; i++) {
            button = new MaterialButton(this, null, style);
            button.setTag(i);
            button.setText(classlist[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = defaults.edit();
                    String selected = String.valueOf(v.getTag());
                    int id = v.getId();
                    editor.putString("selectedClass", selected);
                    editor.putInt("selectedClassID", id);
                    editor.apply();
                    //   Log.i("Info", "Class: " + selected);
                }
            });
            classGroup.addView(button);
        }
    }

    private void addListeners() {
        enteredByTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                enteredByTextInput.getEditText().setText("OK");
                Log.i("Info", "onFocusChange: ");
            }
        });

        modeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                editor = defaults.edit();
                switch (checkedId) {
                    case -1:
                        break;
                    case R.id.myselfRadioButton:
                        enteredByTextInput.setVisibility(View.GONE);
                        editor.putInt("mode", 0);
                        break;
                    case R.id.otherRadioButton:
                        enteredByTextInput.setVisibility(View.VISIBLE);
                        editor.putInt("mode", 1);
                        break;
                    default:
                        break;
                }
                editor.apply();
            }
        });
    }
}