package com.hayroyal.tom.diabetial;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class TestResultEditActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    private Toolbar mToolbar;
    private EditText mTitleText;
    private TextView mDateText, mTimeText, mBpText, mBloText, mCholeText, mBodyText;

    private String mTitle;
    private String mTime;
    private String mDate;
    private String mBloodpessure;
    private String mBloodsugar;
    private String mActive;
    private String mCholesterol;
    private String mBodyweight;
    private String[] mDateSplit;
    private String[] mTimeSplit;
    private int mReceivedID;
    private int mYear, mMonth, mHour, mMinute, mDay;

    private Calendar mCalendar;
    private TestResult mReceivedTestResult;
    private ReminderDatabase rb;

    // Constant Intent String
    public static final String EXTRA_TESTRESULT_ID = "Reminder_ID";

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_BP = "bp_key";
    private static final String KEY_BODY = "body_key";
    private static final String KEY_CHOLE= "Chole_key";
    private static final String KEY_BLO = "blo_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result_add);

        // Initialize Views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleText = (EditText) findViewById(R.id.reminder_title);
        mDateText = (TextView) findViewById(R.id.set_date);
        mTimeText = (TextView) findViewById(R.id.set_time);
        mBloText = (TextView) findViewById(R.id.set_bloodsugar);
        mBodyText = (TextView) findViewById(R.id.set_bodyweight);
        mBpText = (TextView) findViewById(R.id.set_bloodpressure);
        mCholeText = (TextView) findViewById(R.id.set_Chole);


        // Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_activity_edit_reminder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setup Reminder Title EditText
        mTitleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Get reminder id from intent
        mReceivedID = Integer.parseInt(getIntent().getStringExtra(EXTRA_TESTRESULT_ID));

        // Get reminder using reminder id
        rb = new ReminderDatabase(this);
        mReceivedTestResult = rb.getTestResult(mReceivedID);

        // Get values from reminder
        mTitle = mReceivedTestResult.getTitle();
        mDate = mReceivedTestResult.getDate();
        mTime = mReceivedTestResult.getTime();
        mBloodsugar = mReceivedTestResult.getBloodsugar();
        mBloodpessure = mReceivedTestResult.getBloodpressure();
        mBodyweight = mReceivedTestResult.getBodyweight();
        mCholesterol = mReceivedTestResult.getCholesterol();
        mActive = mReceivedTestResult.getActive();

        // Setup TextViews using reminder values
        mTitleText.setText(mTitle);
        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mBpText.setText(mBloodpessure);
        mBodyText.setText(mBodyweight);
        mBloText.setText(mBloodsugar);
        mCholeText.setText(mCholesterol);

        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String savedBloodSugar = savedInstanceState.getString(KEY_BLO);
            mBloText.setText(savedBloodSugar);
            mBloodsugar = savedBloodSugar;

            String savedBloodPressure = savedInstanceState.getString(KEY_BP);
            mBpText.setText(savedBloodPressure);
            mBloodpessure = savedBloodPressure;

            String savedBodyWeight = savedInstanceState.getString(KEY_BODY);
            mBodyText.setText(savedBodyWeight);
            mBodyweight = savedBodyWeight;

            String savedCholesterol = savedInstanceState.getString(KEY_CHOLE);
            mCholeText.setText(savedCholesterol);
            mCholesterol = savedCholesterol;

            mActive = savedInstanceState.getString(KEY_ACTIVE);
        }

        // Obtain Date and Time details
        mCalendar = Calendar.getInstance();

        mDateSplit = mDate.split("/");
        mTimeSplit = mTime.split(":");

        mDay = Integer.parseInt(mDateSplit[0]);
        mMonth = Integer.parseInt(mDateSplit[1]);
        mYear = Integer.parseInt(mDateSplit[2]);
        mHour = Integer.parseInt(mTimeSplit[0]);
        mMinute = Integer.parseInt(mTimeSplit[1]);
    }

    // To save state on device rotation
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, mTitleText.getText());
        outState.putCharSequence(KEY_TIME, mTimeText.getText());
        outState.putCharSequence(KEY_DATE, mDateText.getText());
        outState.putCharSequence(KEY_BP, mBpText.getText());
        outState.putCharSequence(KEY_BODY, mBodyText.getText());
        outState.putCharSequence(KEY_BLO, mBloText.getText());
        outState.putCharSequence(KEY_CHOLE, mCholeText.getText());
        outState.putCharSequence(KEY_ACTIVE, mActive);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    // On clicking Time picker
    public void setTime(View v){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    // On clicking Date picker
    public void setDate(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        mTimeText.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        mDateText.setText(mDate);
    }

    // On clicking the update button
    public void updateTestResult(){
        // Set new values in the reminder
        mReceivedTestResult.setTitle(mTitle);
        mReceivedTestResult.setDate(mDate);
        mReceivedTestResult.setTime(mTime);
        mReceivedTestResult.setBloodpressure(mBloodpessure);
        mReceivedTestResult.setBloodsugar(mBloodsugar);
        mReceivedTestResult.setBodyweight(mBodyweight);
        mReceivedTestResult.setCholesterol(mCholesterol);
        mReceivedTestResult.setActive(mActive);

        // Update reminder
        rb.updateTestResult(mReceivedTestResult);

        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);


        // Create toast to confirm update
        Toast.makeText(getApplicationContext(), "Edited",
                Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_testresult, menu);
        return true;
    }

    // On clicking menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // On clicking the back arrow
            // Discard any changes
            case android.R.id.home:
                onBackPressed();
                return true;

            // On clicking save reminder button
            // Update reminder
            case R.id.save_testresult:
                mTitleText.setText(mTitle);

                if (mTitleText.getText().toString().length() == 0)
                    mTitleText.setError("Reminder Title cannot be blank!");

                else {
                    updateTestResult();
                }
                return true;

            // On clicking discard reminder button
            // Discard any changes
            case R.id.discard_testresult:
                Toast.makeText(getApplicationContext(), "Changes Discarded",
                        Toast.LENGTH_SHORT).show();

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}