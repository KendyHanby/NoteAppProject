package com.cw.m_note

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
//import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.cw.m_note.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.ResponseBody;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.btnMy.setOnClickListener {
            var text = binding.edt.text.toString()
            val r = Retrofit.Builder()
                .baseUrl("http://thekanyeproject.getenjoyment.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Ser::class.java)
            r.get().enqueue(object: Callback<ResponseBody>{
                    override fun onResponse(p: Call<ResponseBody>, r: Response<ResponseBody>){
                        text = r.body()?.string().toString()
                        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
                    }
                    override fun onFailure(p: Call<ResponseBody>, e: Throwable) {
                        text = e.message.toString()
                        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
                    }
                })
        	Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }

    interface Ser {
        @GET("/api.php")
        fun get(): Call<ResponseBody>
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        super.onOptionsItemSelected(menuItem)
        if (menuItem.title == "Item") {
            return true
        }
        return false
    }
}