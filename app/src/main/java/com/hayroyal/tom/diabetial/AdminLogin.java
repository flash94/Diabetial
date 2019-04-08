package com.hayroyal.tom.diabetial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//created by Folalu Timothy 02/02/2019
public class AdminLogin extends AppCompatActivity {

    EditText editText;
    Button button;

    String passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

//        get the password for sharedpreferences
        SharedPreferences settings = getSharedPreferences("PREFSS",0);
        passwords = settings.getString("passwords", "");

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();

                if(text.equals(passwords)){
                    //                        login to the app
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AdminLogin.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}