package com.sxi.notes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.sxi.notes.adapter.MainPagerAdapter;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.data.model.NoteModel;
import com.sxi.notes.data.model.TaskModel;
import com.sxi.notes.databinding.ActivityMainBinding;
import com.sxi.notes.service.MyNotificationReceiver;
import com.sxi.notes.ui.NoteFragment;
import com.sxi.notes.ui.TaskFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    MySqlHelper db;
    private ActivityMainBinding binding;
    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        db = new MySqlHelper(getApplicationContext());
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyNotificationReceiver.class);
        intent.putExtra("message", "Don't forget to do your task!");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // Set the reminder to go off at a specific time
        Calendar calendar = Calendar.getInstance();
        /*calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);*/
        long timeInMillis = calendar.getTimeInMillis()+5000;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);



        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setFrag(0);

        // We don't use it. It mess up code.
        //Icon selected color
        //setupTabIcons();
        binding.tabTool.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFrag(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setFrag(int p){
        Fragment fragment = new NoteFragment();
        if (p==1){
            fragment = new TaskFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
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
        if (item.getTitle().equals("Settings")) {
            startActivity(new Intent(getApplicationContext(), SettingAv.class));
        }
        return super.onOptionsItemSelected(item);
    }
}