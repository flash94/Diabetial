package com.hayroyal.tom.diabetial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginForAdminAndUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin_user);

    }

    public void userLoginClicked (View v) {
        Intent i = new Intent(this, LoginActivityUser.class);
        startActivity(i);
    }

    public void adminLoginClicked(View v) {
        Intent Admin = new Intent(this,AdminLogin.class );
        startActivity(Admin);
        finish();
    }
    public void registerHereClicked (View view) {
        Intent register = new Intent(this, UserAndAdminRegister.class);
        startActivity(register);
        finish();
    }
}
