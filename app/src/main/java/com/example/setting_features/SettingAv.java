package com.example.setting_features;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingAv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_av);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl, new SettingFragment()).commit();


    }


}