package com.sxi.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.sxi.notes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    MySqlHelper mySqlHelper;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int range = appBarLayout.getTotalScrollRange();
            boolean isCollapse = false;
            if (range+verticalOffset==0){
                isCollapse=true;
            } else if (isCollapse) {
                isCollapse = false;
            }
            if (isCollapse){
                binding.tabTool.setVisibility(View.VISIBLE);
            }else {
                binding.tabTool.setVisibility(View.GONE);
            }
        });

        //Building SQLiteDatabase
       mySqlHelper = new MySqlHelper(getApplicationContext());
        db = mySqlHelper.getWritableDatabase();


/*
        binding.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingAv.class));
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem settings = menu.add("Settings");
        settings.setIcon(R.drawable.outline_settings_24);
        settings.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Settings")){
            startActivity(new Intent(getApplicationContext(),SettingAv.class));
        }
        return super.onOptionsItemSelected(item);
    }



  /*
        private void addDataToSqlDatabase(){
        String txt = xxx.getText().toString();

        mySqlHelper.dbOpen();
        long id = mySqlHelper.dataInsert(txt);
        mySqlHelper.dbclose();
        if(id < 0){
        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }else{
        Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_LONG).show();
        }
        }
    */

    /*
           private void startQuery(){
           mySqlHelper.dbOpen();
           String st = mySqlHelper.dataQuery()
           mySqlHelper.dbclose();

           }

     */
}