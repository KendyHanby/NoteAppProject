package com.sxi.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.sxi.notes.data.MySqlHelper;
import com.sxi.notes.databinding.ActivityMainBinding;
import com.sxi.notes.ui.NoteFragment;
import com.sxi.notes.ui.TaskFragment;

public class MainActivity extends AppCompatActivity {

    MySqlHelper db;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        db = new MySqlHelper(getApplicationContext());

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