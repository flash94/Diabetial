package com.hayroyal.tom.diabetial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;


public class Main2Activity extends AppCompatActivity {
    private RecyclerView mList;
    private SimpleAdapter mAdapter;
    private Toolbar mToolbar;
    private TextView mNoReminderView;
    private FloatingActionButton mAddReminderButton;
    private int mTempPost;
    private LinkedHashMap<Integer, Integer> IDmap = new LinkedHashMap<>();
    private ReminderDatabase rb;
    private MultiSelector mMultiSelector = new MultiSelector();
    private AlarmReceiver mAlarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize reminder database
        rb = new ReminderDatabase(getApplicationContext());

        // Initialize views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddReminderButton = (FloatingActionButton) findViewById(R.id.add_reminder1);
        mList = (RecyclerView) findViewById(R.id.reminder_list1);
        mNoReminderView = (TextView) findViewById(R.id.no_reminder_text1);

        // To check is there are saved reminders
        // If there are no reminders display a message asking the user to create reminders
        List<TestResult> mTest = rb.getAllTestResult();

        if (mTest.isEmpty()) {
            mNoReminderView.setVisibility(View.VISIBLE);
        }

        // Create recycler view
        mList.setLayoutManager(
                getLayoutManager());
        registerForContextMenu(mList);
        mAdapter = new SimpleAdapter();
        mAdapter.setItemCount(getDefaultItemCount());
        mList.setAdapter(mAdapter);

        // Setup toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);

        // On clicking the floating action button
        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TestResultAddActivity.class);
                startActivity(intent);
            }
        });

        // Initialize alarm
        mAlarmReceiver = new AlarmReceiver();
    }

    // Create context menu for long press actions
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_add_testresult, menu);
    }

    // Multi select items in recycler view
    private android.support.v7.view.ActionMode.Callback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_add_testresult, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                // On clicking discard reminders
                case R.id.discard_testresult:
                    // Close the context menu
                    actionMode.finish();

                    // Get the reminder id associated with the recycler view item
                    for (int i = IDmap.size(); i >= 0; i--) {
                        if (mMultiSelector.isSelected(i, 0)) {
                            int id = IDmap.get(i);

                            // Get reminder from reminder database using id
                            TestResult temp = rb.getTestResult(id);
                            // Delete reminder
                            rb.deleteTestResult(temp);
                            // Remove reminder from recycler view
                            mAdapter.removeItemSelected(i);
                            // Delete reminder alarm
                            mAlarmReceiver.cancelAlarm(getApplicationContext(), id);
                        }
                    }

                    // Clear selected items in recycler view
                    mMultiSelector.clearSelections();
                    // Recreate the recycler items
                    // This is done to remap the item and reminder ids
                    mAdapter.onDeleteItem(getDefaultItemCount());

                    // Display toast to confirm delete
                    Toast.makeText(getApplicationContext(),
                            "Deleted",
                            Toast.LENGTH_SHORT).show();

                    // To check is there are saved reminders
                    // If there are no reminders display a message asking the user to create reminders
                    List<TestResult> mTest = rb.getAllTestResult();

                    if (mTest.isEmpty()) {
                        mNoReminderView.setVisibility(View.VISIBLE);
                    } else {
                        mNoReminderView.setVisibility(View.GONE);
                    }

                    return true;

                // On clicking save reminders
                case R.id.save_testresult:
                    // Close the context menu
                    actionMode.finish();
                    // Clear selected items in recycler view
                    mMultiSelector.clearSelections();
                    return true;

                default:
                    break;
            }
            return false;
        }
    };

    // On clicking a reminder item
    private void selectReminder(int mClickID) {
        String mStringClickID = Integer.toString(mClickID);

        // Create intent to edit the reminder
        // Put reminder id as extra
        Intent i = new Intent(this, TestResultEditActivity.class);
        i.putExtra(TestResultEditActivity.EXTRA_TESTRESULT_ID, mStringClickID);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAdapter.setItemCount(getDefaultItemCount());
    }

    // Recreate recycler view
    // This is done so that newly created reminders are displayed
    @Override
    public void onResume(){
        super.onResume();

        // To check is there are saved test results
        // If there are no test result display a message asking the user to create test result
        List<TestResult> mTest = rb.getAllTestResult();

        if (mTest.isEmpty()) {
            mNoReminderView.setVisibility(View.VISIBLE);
        } else {
            mNoReminderView.setVisibility(View.GONE);
        }

        mAdapter.setItemCount(getDefaultItemCount());
    }

    // Layout manager for recycler view
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    protected int getDefaultItemCount() {
        return 100;
    }

    // Create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Setup menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // start licenses activity
            case R.id.action_licenses:
                Intent intent = new Intent(this, AdminLogin.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Adapter class for recycler view
    public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {
        private ArrayList<TestResultItem> mItems;

        public SimpleAdapter() {
            mItems = new ArrayList<>();
        }

        public void setItemCount(int count) {
            mItems.clear();
            mItems.addAll(generateData(count));
            notifyDataSetChanged();
        }

        public void onDeleteItem(int count) {
            mItems.clear();
            mItems.addAll(generateData(count));
        }

        public void removeItemSelected(int selected) {
            if (mItems.isEmpty()) return;
            mItems.remove(selected);
            notifyItemRemoved(selected);
        }

        // View holder for recycler view items
        @Override
        public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View root = inflater.inflate(R.layout.recycle_items, container, false);

            return new VerticalItemHolder(root, this);
        }

        @Override
        public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
            TestResultItem item = mItems.get(position);
            itemHolder.setReminderTitle(item.mTitle);
            itemHolder.setReminderDateTime(item.mDateTime);
//            itemHolder.setBodyweight(item.mBodyweight);
            itemHolder.setBp(item.mBloodpressure);
//            itemHolder.setChole(item.mCholesterol);
//            itemHolder.setBlo(item.mBloodsugar);
            Log.println(Log.ASSERT, "LOH", (!TextUtils.isEmpty(item.mActive) && item.mActive.equals("true")) + "");
            itemHolder.setActiveImage((!TextUtils.isEmpty(item.mActive) && item.mActive.equals("true")) + "");
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        // Class for recycler view items
        public  class TestResultItem {
            public String mTitle;
            private String mBodyweight;
            private String mBloodsugar;
            private String mBloodpressure;
            private String mDateTime;
            private String mCholesterol;
            private String mActive;

            public TestResultItem(String mTitle, String mBodyweight, String mBloodpressure, String mBloodsugar, String mDateTime, String mCholesterol, String mActive) {
                this.mTitle = mTitle;
                this.mBodyweight = mBodyweight;
                this.mBloodpressure = mBloodpressure;
                this.mBloodsugar = mBloodsugar;
                this.mDateTime = mDateTime;
                this.mCholesterol = mCholesterol;
                this.mActive = mActive;
            }
        }

        // Class to compare date and time so that items are sorted in ascending order
        public class DateTimeComparator implements Comparator {
            DateFormat f = new SimpleDateFormat("dd/mm/yyyy hh:mm");

            public int compare(Object a, Object b) {
                String o1 = ((DateTimeSorter)a).getDateTime();
                String o2 = ((DateTimeSorter)b).getDateTime();

                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }

        // UI and data class for recycler view items
        public  class VerticalItemHolder extends SwappingHolder
                implements View.OnClickListener, View.OnLongClickListener {
            private TextView mTitleText, mDateAndTimeText, mBloodPressure, mBloodSugar, mCholesterol, mBodyWeight;
            private ImageView mActiveImage , mThumbnailImage;
            private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
            private TextDrawable mDrawableBuilder;
            private SimpleAdapter mAdapter;

            public VerticalItemHolder(View itemView, SimpleAdapter adapter) {
                super(itemView, mMultiSelector);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setLongClickable(true);

                // Initialize adapter for the items
                mAdapter = adapter;

                // Initialize views
                mTitleText = (TextView) itemView.findViewById(R.id.recycle_title);
                mDateAndTimeText = (TextView) itemView.findViewById(R.id.recycle_date_time);
                mBloodPressure = (TextView) itemView.findViewById(R.id.recycle_repeat_info);
                mActiveImage = (ImageView) itemView.findViewById(R.id.active_image);
                mThumbnailImage = (ImageView) itemView.findViewById(R.id.thumbnail_image);
            }

            // On clicking a reminder item
            @Override
            public void onClick(View v) {
                if (!mMultiSelector.tapSelection(this)) {
                    mTempPost = mList.getChildAdapterPosition(v);

                    int mReminderClickID = IDmap.get(mTempPost);
                    selectReminder(mReminderClickID);

                } else if(mMultiSelector.getSelectedPositions().isEmpty()){
                    mAdapter.setItemCount(getDefaultItemCount());
                }
            }

            // On long press enter action mode with context menu
            @Override
            public boolean onLongClick(View v) {
                AppCompatActivity activity = Main2Activity.this;
                activity.startSupportActionMode(mDeleteMode);
                mMultiSelector.setSelected(this, true);
                return true;
            }

            // Set reminder title view
            public void setReminderTitle(String title) {
                mTitleText.setText(title);
                String letter = "A";

                if(title != null && !title.isEmpty()) {
                    letter = title.substring(0, 1);
                }

                int color = mColorGenerator.getRandomColor();

                // Create a circular icon consisting of  a random background colour and first letter of title
                mDrawableBuilder = TextDrawable.builder()
                        .buildRound(letter, color);
                mThumbnailImage.setImageDrawable(mDrawableBuilder);
            }

            // Set date and time views
            public void setReminderDateTime(String datetime) {
                mDateAndTimeText.setText(datetime);
            }

            public void setBp (String bloodpressure) {
                mBloodPressure.setText(bloodpressure);
            }



            // Set active image as on or off
            public void setActiveImage(String active){
                if(active.equals("true")){
                    mActiveImage.setImageResource(R.drawable.ic_notifications_on_white_24dp);
                }else if (active.equals("false")) {
                    mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
                }
            }
        }

        // Generate random test data
        public  TestResultItem generateDummyData() {
            return new TestResultItem("1", "2", "3", "4", "5",  "6", "7");
        }

        // Generate real data for each item
        public List<TestResultItem> generateData(int count) {
            ArrayList<SimpleAdapter.TestResultItem> items = new ArrayList<>();

            // Get all reminders from the database
            List<TestResult> testResults = rb.getAllTestResult();

            // Initialize lists
            List<String> Titles = new ArrayList<>();
            List<String> Body = new ArrayList<>();
            List<String> Blo = new ArrayList<>();
            List<String> Bp = new ArrayList<>();
            List<String> Chole = new ArrayList<>();
            List<String> Actives = new ArrayList<>();
            List<String> DateAndTime = new ArrayList<>();
            List<Integer> IDList= new ArrayList<>();
            List<DateTimeSorter> DateTimeSortList = new ArrayList<>();

            // Add details of all reminders in their respective lists
            for (TestResult t : testResults) {
                Titles.add(t.getTitle());
                DateAndTime.add(t.getDate() + " " + t.getTime());
                Blo.add(t.getBloodsugar());
                Body.add(t.getBodyweight());
                Bp.add(t.getBloodpressure());
                Chole.add(t.getCholesterol());
                Actives.add(t.getActive());
                IDList.add(t.getID());
            }

            int key = 0;

            // Add date and time as DateTimeSorter objects
            for(int k = 0; k<Titles.size(); k++){
                DateTimeSortList.add(new DateTimeSorter(key, DateAndTime.get(k)));
                key++;
            }

            // Sort items according to date and time in ascending order
            Collections.sort(DateTimeSortList, new DateTimeComparator());

            int k = 0;

            // Add data to each recycler view item
            for (DateTimeSorter item:DateTimeSortList) {
                int i = item.getIndex();

                items.add(new SimpleAdapter.TestResultItem(Titles.get(i), DateAndTime.get(i), Blo.get(i),
                        Bp.get(i), Body.get(i), Chole.get(i), Actives.get(i)));
                IDmap.put(k, IDList.get(i));
                k++;
            }
            return items;
        }
    }
}