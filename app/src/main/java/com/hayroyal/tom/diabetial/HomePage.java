package com.hayroyal.tom.diabetial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

//created by Folalu Tomiwa 20/03/2019
public class HomePage extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("DIABETIAL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Return to the previous activity on back press
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });
    }

    public void healthClicked(View view){
        Intent health = new Intent (this, Main2Activity.class);
        startActivity(health);
//        finish();
    }

    public void reminderClicked(View view){
        Intent reminder = new Intent (this, MainActivity.class);
        startActivity(reminder);
//        finish();
    }
    public void exerciseClicked (View view){
        Intent exercise = new Intent (this, ExerciseTips.class);
        startActivity(exercise);
//        finish();
    }
    public void dietClicked (View view){
        Intent diet = new Intent (this, RecommendedFood.class);
        startActivity(diet);
//        finish();
    }
    public void stayClicked (View view){
        Intent stay = new Intent (this, TipsforDiabetics.class);
        startActivity(stay);
//        finish();
    }
    public void bmiClicked (View view) {
        Intent bmi = new Intent(this, BmiCalculator.class);
        startActivity(bmi);
//        finish();
    }
}
