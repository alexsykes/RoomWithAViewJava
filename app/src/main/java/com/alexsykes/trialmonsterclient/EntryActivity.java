
package com.alexsykes.trialmonsterclient;

// https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
// https://stackoverflow.com/questions/57212800/how-to-return-the-results-form-a-datepickerfragment-to-a-fragment
// https://stackoverflow.com/questions/67647393/post-request-with-volley-with-headers-and-body-java-android-studio
// https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/* TODO
 *   Tidy-up datepicker fragment
 *
 *
 *
 * */

public class EntryActivity extends AppCompatActivity {
    String TAG = "Info";
    TextInputLayout firstNameTextInput, lastnameTextInput, emailTextInput, enteredByTextInput, acuTextInput, pgTextInput, sizeTextInput, makeTextInput;
    RadioGroup modeGroup;
    RadioButton myselfRadioButton, otherRadioButton;
    int mode;
    int selectedCourse, selectedClass, selectedType;
    boolean isYouth;
    MaterialButtonToggleGroup courseGroup, classGroup, typeGroup;
    Button dateButton;
    String dob, dateForButton;
    String courses, classes, courseSelected, classSelected, make, size,
            typeSelected, pgName, firstname, lastname, enteredBy, acu, email, trialid;
    String[] courselist, classlist, types;
    LinearLayout youthStack, dobStack;

    SharedPreferences defaults;
    SharedPreferences.Editor editor;
    private URL url;

    // MARK: Lifecycle events
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        // Get course / class data from intent
        trialid = getIntent().getStringExtra("trialid");
        courses = getIntent().getStringExtra("courses");
        classes = getIntent().getStringExtra("classes");
        courselist = courses.split(",");
        classlist = classes.split(",");
        types = new String[]{"2T", "4T", "e-bike"};

        // Get saved values from prefs
        defaults = this.getPreferences(Context.MODE_PRIVATE);
        setUpUI();
        setupInitialValues();
        addListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        editor = defaults.edit();
        editor.putString("email", emailTextInput.getEditText().getText().toString());
        editor.putString("enteredBy", enteredByTextInput.getEditText().getText().toString());
        editor.putString("firstname", firstNameTextInput.getEditText().getText().toString());
        editor.putString("lastname", lastnameTextInput.getEditText().getText().toString());
        editor.putString("acu", acuTextInput.getEditText().getText().toString());
        editor.putString("pgName", pgTextInput.getEditText().getText().toString());
        editor.putString("acu", acuTextInput.getEditText().getText().toString());
        editor.putString("make", makeTextInput.getEditText().getText().toString());
        editor.putString("size", sizeTextInput.getEditText().getText().toString());
        editor.putInt("mode", mode);

        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_make_entry:
                uploadEntry();
                //postRequest();
//                try {
//                    makeEntry();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//
                return true;
            default:

        }
        return false;
    }

    private void postRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        final String url = "https://android.trialmonster.uk/test.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void getRequest() {
        final String url = "https://httpbin.org/get?param1=hello";
        final String url1 = "https://android.trialmonster.uk/test.php";
        RequestQueue queue = Volley.newRequestQueue(this);
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }

    // MARK: Utility methods
    private void setUpUI() {
        firstNameTextInput = findViewById(R.id.firstnameTextInput);
        lastnameTextInput = findViewById(R.id.lastnameTextInput);
        emailTextInput = findViewById(R.id.emailTextInput);
        enteredByTextInput = findViewById(R.id.enteredByTextInput);
        acuTextInput = findViewById(R.id.acuTextInput);
        pgTextInput = findViewById(R.id.pgTextInput);
        makeTextInput = findViewById(R.id.makeTextInput);
        sizeTextInput = findViewById(R.id.sizeTextInput);

        modeGroup = findViewById(R.id.modeGroup);
        myselfRadioButton = findViewById(R.id.myselfRadioButton);
        otherRadioButton = findViewById(R.id.otherRadioButton);

        // youthSwitch = findViewById(R.id.youthSwitch);
        // youthStack = findViewById(R.id.youthStack);
        dobStack = findViewById(R.id.dobStack);
        dateButton = findViewById(R.id.dateButton);

        // Initialise
        classGroup = findViewById(R.id.classGroup);
        classGroup.setSelectionRequired(true);
        classGroup.setSingleSelection(true);

        courseGroup = findViewById(R.id.courseGroup);
        courseGroup.setSelectionRequired(true);
        courseGroup.setSingleSelection(true);

        typeGroup = findViewById(R.id.typeGroup);
        typeGroup.setSelectionRequired(true);
        typeGroup.setSingleSelection(true);

        int style = R.attr.materialButtonOutlinedStyle;

        selectedClass = defaults.getInt("selectedClass", -1);
        selectedCourse = defaults.getInt("selectedCourse", -1);
        selectedType = defaults.getInt("selectedType", -1);

        for (int i = 0; i < classlist.length; i++) {
            MaterialButton button = new MaterialButton(this, null, style);
            button.setTag(i);
            button.setText(classlist[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = defaults.edit();
                    classSelected = String.valueOf(button.getText());
                    String selected = String.valueOf(v.getTag());

                    if (classSelected.toLowerCase().contains("youth")) {
                        isYouth = true;
                        // youthStack.setVisibility(View.VISIBLE);
                        dobStack.setVisibility(View.VISIBLE);
                        pgTextInput.setVisibility(View.VISIBLE);
                    } else {
                        isYouth = false;
                        // youthStack.setVisibility(View.GONE);
                        dobStack.setVisibility(View.GONE);
                        pgTextInput.setVisibility(View.GONE);
                    }

                    editor.putString("classSelected", classSelected);
                    editor.putInt("selectedClass", Integer.parseInt(selected));
                    editor.putBoolean("isYouth", isYouth);
                    editor.apply();
                }
            });
            classGroup.addView(button);
        }
        if (selectedClass > -1) {
            classGroup.check(classGroup.getChildAt(selectedClass).getId());
        }

        for (int i = 0; i < courselist.length; i++) {
            MaterialButton button = new MaterialButton(this, null, style);
            button.setTag(i);
            button.setText(courselist[i]);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = defaults.edit();
                    courseSelected = String.valueOf(button.getText());
                    String selected = String.valueOf(v.getTag());

                    editor.putString("courseSelected", courseSelected);
                    editor.putInt("selectedCourse", Integer.parseInt(selected));
                    editor.apply();
                }
            });
            courseGroup.addView(button);
        }
        if (selectedCourse > -1) {
            courseGroup.check(courseGroup.getChildAt(selectedCourse).getId());
        }

        for (int i = 0; i < types.length; i++) {
            MaterialButton button = new MaterialButton(this, null, style);
            button.setTag(i);
            button.setText(types[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = defaults.edit();
                    typeSelected = String.valueOf(button.getText());
                    String selected = String.valueOf(v.getTag());

                    editor.putString("typeSelected", typeSelected);
                    editor.putInt("selectedType", Integer.parseInt(selected));
                    selectedType = Integer.parseInt(selected);
                    editor.apply();
                }
            });
            typeGroup.addView(button);
        }
        if (selectedType > -1) {
            typeGroup.check(typeGroup.getChildAt(selectedType).getId());
        }
    }

    private void setupInitialValues() {
        modeGroup.check(R.id.myselfRadioButton);
        enteredByTextInput.setVisibility(View.GONE);
        defaults = this.getPreferences(Context.MODE_PRIVATE);
        mode = defaults.getInt("mode", 0);
        courseSelected = defaults.getString("courseSelected", courseSelected);
        classSelected = defaults.getString("classSelected", classSelected);
        isYouth = classSelected != null && classSelected.toLowerCase().contains("youth");
        enteredBy = defaults.getString("enteredBy", "");
        email = defaults.getString("email", "");
        firstname = defaults.getString("firstname", "");
        lastname = defaults.getString("lastname", "");
        acu = defaults.getString("acu", "");
        pgName = defaults.getString("pgName", "");
        make = defaults.getString("make", "");
        size = defaults.getString("size", "");
        typeSelected = defaults.getString("typeSelected", "");
        dob = defaults.getString("dob", "");

        if (mode == 0) {
            enteredByTextInput.setVisibility(View.GONE);
            modeGroup.check(R.id.myselfRadioButton);
        } else if (mode == 1) {
            enteredByTextInput.setVisibility(View.VISIBLE);
            modeGroup.check(R.id.otherRadioButton);
        }

        enteredByTextInput.getEditText().setText(enteredBy);
        firstNameTextInput.getEditText().setText(firstname);
        lastnameTextInput.getEditText().setText(lastname);
        emailTextInput.getEditText().setText(email);
        acuTextInput.getEditText().setText(acu);
        pgTextInput.getEditText().setText(pgName);
        makeTextInput.getEditText().setText(make);
        sizeTextInput.getEditText().setText(size);

        dateForButton = formatDate(dob);
        dateButton.setText(dateForButton);

        if (isYouth) {
            //  youthStack.setVisibility(View.VISIBLE);
            dobStack.setVisibility(View.VISIBLE);
            pgTextInput.setVisibility(View.VISIBLE);
        } else {
            //   youthStack.setVisibility(View.GONE);
            dobStack.setVisibility(View.GONE);
            pgTextInput.setVisibility(View.GONE);
        }
    }

    private void addListeners() {
        enteredByTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                enteredBy = enteredByTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("enteredBy", enteredBy);
                editor.apply();
            }
        });

        emailTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email = emailTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("email", email);
                editor.apply();
            }
        });

        firstNameTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                firstname = firstNameTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("firstname", firstname);
                editor.apply();
            }
        });

        lastnameTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                lastname = lastnameTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("lastname", lastname);
                editor.apply();
            }
        });

        acuTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                acu = acuTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("acu", acu);
                editor.apply();
            }
        });

        pgTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pgName = pgTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("pgName", pgName);
                editor.apply();
            }
        });

        makeTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                make = makeTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("make", make);
                editor.apply();
            }
        });

        sizeTextInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                size = sizeTextInput.getEditText().getText().toString();
                editor = defaults.edit();
                editor.putString("size", size);
                editor.apply();
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
                        mode = 0;
                        enteredByTextInput.setVisibility(View.GONE);
                        editor.putInt("mode", 0);
                        break;
                    case R.id.otherRadioButton:
                        mode = 1;
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

    public void updateDoB(int year, int month, int day) {
        Log.i("Info", "updateDoB: ");
        dateButton = findViewById(R.id.dateButton);

        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String date = (year_string + "-" + month_string + "-" + day_string);
        editor = defaults.edit();
        editor.putString("dob", date);
        editor.apply();
        dateForButton = formatDate(date);
        dateButton.setText(dateForButton);
    }

    public void uploadEntry() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://android.trialmonster.uk/uploadEntryData.php";
        JSONArray data = new JSONArray();

        data.put(firstname);
        data.put(lastname);
        data.put(acu);
        data.put(classSelected);
        data.put(courseSelected);
        data.put(email);
        data.put(enteredBy);
        data.put(make);
        data.put(size);
        data.put(trialid);
        data.put(pgName);
        data.put(isYouth);
        data.put(selectedType);
        data.put(dob);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                // request body goes here

                String requestBody = data.toString();
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }

        };


        Log.d("string", stringRequest.toString());
        requestQueue.add(stringRequest);
    }

    private void showResponse(String response) {
        // try {
        // JSONArray jsonArray = new JSONArray(response);
        Log.i("VOLLEY", "showResponse: " + response);
//        } catch (JSONException e) {
//            Log.i("VOLLEY", "printStackTrace: " );
//            e.printStackTrace();
//        }
    }

    private void makeEntry() throws MalformedURLException {
        // Prepare data for upload

        String[] fields = {courseSelected, classSelected, make, size,
                typeSelected, pgName, firstname, lastname, enteredBy, acu, email, trialid};

        String header = "courseSelected, classSelected, make, size,\n" +
                "                typeSelected, pgName, firstname, lastname, enteredBy, acu, email, trialid";
        Log.i(TAG, "fields: " + String.join(",", fields));
        Log.i(TAG, "header: " + header);

        url = new URL("http://android.trialmonster.uk/uploadEntryData.php/");
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //  writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //  readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }

    private String formatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

        try {
            Date prefsdate = format.parse(date);
            format = new SimpleDateFormat("MMM d, yyyy");
            dateForButton = format.format(prefsdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateForButton;
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year, month, day;
            final Calendar c = Calendar.getInstance();
            SharedPreferences defaults = getActivity().getPreferences(Context.MODE_PRIVATE);
            String date = defaults.getString("dob", "");
            if (date == "") {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            } else {
                String[] dateBits = date.split("-");
                year = Integer.parseInt(dateBits[0]);
                month = Integer.parseInt(dateBits[1]) - 1;
                day = Integer.parseInt(dateBits[2]);
            }

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            ((EntryActivity) getActivity()).updateDoB(year, month, day);
        }

    }
}