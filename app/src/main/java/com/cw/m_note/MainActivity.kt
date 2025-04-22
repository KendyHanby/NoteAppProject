package com.cw.m_note

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.cw.m_note.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // setup tab layout
        val notes = binding.mainTab.newTab().setText("Notes").setId(View.generateViewId())
        val tasks = binding.mainTab.newTab().setText("Tasks").setId(View.generateViewId())
        binding.mainTab.addTab(notes)
        binding.mainTab.addTab(tasks)
        binding.mainTab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.mainPager.currentItem =
                    if (tab.id == notes.id) {
                        0
                    } else {
                        1
                    }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        // setup FAB with app bar layout
        binding.appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset == 0 && binding.mainFab.isShown.not()) {
                binding.mainFab.show()
            }
        }

        // set up pager
        binding.mainPager.adapter = PageAdapter(this)
        binding.mainPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.mainTab.selectTab(
                    if (position == 0) {
                        notes
                    } else {
                        tasks
                    }
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        super.onOptionsItemSelected(menuItem)
        if (menuItem.title == "Item") {
            startActivity(Intent(this, TerActivity::class.java))
            return true
        }
        return false
    }
}