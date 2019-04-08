package com.hayroyal.tom.diabetial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class UserAndAdminRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_and_admin_register);
        // Setup Toolbar


    }

    public void userRegisterClicked (View view) {
        Intent userRegister = new Intent (this, RegisterActivityUser.class);
        startActivity(userRegister);
        finish();
    }
    public void adminRegisterClicked (View view) {
        Intent adminRegister = new Intent (this, RegisterActivityAdmin.class);
        startActivity(adminRegister);
        finish();
    }
    public void loginClicked (View view) {
        Intent login = new Intent (this, LoginForAdminAndUser.class);
        startActivity(login);
        finish();
    }

    // On clicking the back button
    @Override
    public void onBackPressed() {
        Intent back = new Intent(this, LoginForAdminAndUser.class);
        startActivity(back);
        finish();
    }
}
