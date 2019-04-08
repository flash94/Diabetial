package com.hayroyal.tom.diabetial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.hayroyal.tom.diabetial.BmiSpace.BmiType;
import com.hayroyal.tom.diabetial.BmiSpace.Formular;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BmiCalculator extends AppCompatActivity {
private Toolbar  mToolbar;
    Button buttonCalculate, buttonExit;
    EditText inputKg, inputM;
    TextView showResult, showBMI, showImpBMI;
    private double kg, m;
    private DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat(".##");
    Formular metricFormula;
    BmiType bmiCategory = new BmiType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("BMI Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Return to the previous activity on back press
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });

        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonExit = findViewById(R.id.buttonExit);
        inputKg = findViewById(R.id.inputKg);
        inputM = findViewById(R.id.inputM);
        showResult = findViewById(R.id.showResult);
        showBMI = findViewById(R.id.showBMI);


        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kg = Double.parseDouble(inputKg.getText().toString());
                m = Double.parseDouble(inputM.getText().toString());

                metricFormula = new Formular(kg, m);


                showBMI.setText("BMI = " + String.valueOf(TWO_DECIMAL_PLACES.format(metricFormula.computeBMI(metricFormula.getInputKg(),metricFormula.getInputM()))));
                showResult.setText(bmiCategory.getCategory(metricFormula.computeBMI(metricFormula.getInputKg(),metricFormula.getInputM())));

            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
    Intent back = new Intent(this, HomePage.class);
    startActivity(back);
    finish();
    }

}
