package com.hayroyal.tom.diabetial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class TestResultAddActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    private Toolbar mToolbar;
    private EditText mTitleText, mBloText, mBpText, mBodyText, mCholeText;
    private TextView mDateText, mTimeText;

    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTitle;
    private String mBodyweight;
    private String mBloodsugar;
    private String mBloodpressure;
    private String mDate;
    private String mTime;
    private String mCholesterol;
    private String mActive;

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_BLO = "blo_key";
    private static final String KEY_BP = "bp_key";
    private static final String KEY_CHOLE = "repeat_chole";
    private static final String KEY_BODY = "repeat_body";
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
        mBloText = (EditText) findViewById(R.id.set_bloodsugar);
        mBodyText = (EditText) findViewById(R.id.set_bodyweight);
        mBpText = (EditText) findViewById(R.id.set_bloodpressure);
        mCholeText = (EditText) findViewById(R.id.set_Chole);



        // Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Health Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Initialize default values
        mActive = "true";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

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

        mBpText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBloodpressure = s.toString().trim();
                mBpText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mBodyText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBodyweight = s.toString().trim();
                mBodyText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mBloText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBloodsugar = s.toString().trim();
                mBloText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mCholeText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCholesterol = s.toString().trim();
                mCholeText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Setup TextViews using reminder values

        mDateText.setText(mDate);
        mTimeText.setText(mTime);

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

            String savedBp = savedInstanceState.getString(KEY_BP);
            mBpText.setText(savedBp);
            mBloodpressure = savedBp;

            String savedBody = savedInstanceState.getString(KEY_BODY);
            mBodyText.setText(savedBody);
            mBodyweight = savedBody;

            String savedBlo = savedInstanceState.getString(KEY_BLO);
            mBloText.setText(savedBlo);
            mBloodsugar = savedBlo;

            String savedChole = savedInstanceState.getString(KEY_CHOLE);
            mCholeText.setText(savedChole);
            mCholesterol = savedChole;

            mActive = savedInstanceState.getString(KEY_ACTIVE);
        }

    }



    // To save state on device rotation
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, mTitleText.getText());
        outState.putCharSequence(KEY_TIME, mTimeText.getText());
        outState.putCharSequence(KEY_DATE, mDateText.getText());
        outState.putCharSequence(KEY_BP, mBpText.getText());
        outState.putCharSequence(KEY_BLO, mBloText.getText());
        outState.putCharSequence(KEY_BODY, mBodyText.getText());
        outState.putCharSequence(KEY_CHOLE, mCholeText.getText());
        outState.putCharSequence(KEY_ACTIVE, mActive);
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


    // On clicking the save button
    public void saveTestResult(){
        ReminderDatabase rb = new ReminderDatabase(this);

        // Creating Reminder
        int ID = rb.addTestResult(new TestResult(mTitle, mDate, mTime, mBloodpressure, mBloodsugar, mBodyweight, mCholesterol, mActive));

        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);



        // Create toast to confirm new reminder
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

        onBackPressed();
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        Intent back = new Intent(this, Main2Activity.class);
        startActivity(back);
        finish();
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
                    mTitleText.setError("Test Result Title cannot be blank!");

                else {
                    saveTestResult();
                }
                return true;

            // On clicking discard reminder button
            // Discard any changes
            case R.id.discard_testresult:
                Toast.makeText(getApplicationContext(), "Discarded",
                        Toast.LENGTH_SHORT).show();

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}