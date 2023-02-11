package com.sxi.notes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sxi.notes.adapter.MainPagerAdapter;
import com.sxi.notes.databinding.ActivityMainBinding;
import com.sxi.notes.model.NoteModel;

public class MainActivity extends AppCompatActivity {

    MySqlHelper db;
    private ActivityMainBinding binding;
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode()==RESULT_OK) {
            Bundle bundle = result.getData().getExtras();
            String title= bundle.getString("title")
                    ,text=bundle.getString("text");
            long date=bundle.getLong("date");
            int theme=bundle.getInt("theme");
            if (db.saveNote(new NoteModel(title,text,date,theme))!=-1){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            }
        } else if (result.getResultCode()==RESULT_CANCELED){
            Toast.makeText(this, "Cancel note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Others Error", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        db = new MySqlHelper(this);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // We don't use it. It mess up code.
        //Icon selected color
        //setupTabIcons();

        binding.mainPager.setAdapter(new MainPagerAdapter(this));

        binding.mainPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabTool.getTabAt(position).select();
            }
        });
        binding.tabTool.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.mainPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.mainFab.setOnClickListener(view -> {
            launcher.launch(new Intent(getApplicationContext(),NoteEditorActivity.class));
        });
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
    @Deprecated
    /**Using Color State List is better*/
    private void setupTabIcons() {

        binding.tabTool.getTabAt(0).getIcon().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        binding.tabTool.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);

        binding.tabTool.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getColor(R.color.selected_color), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getColor(R.color.unselected_color), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}