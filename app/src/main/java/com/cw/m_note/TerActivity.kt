package com.cw.m_note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.cw.m_note.databinding.ActivityTerBinding
import io.github.rosemoe.sora.langs.java.JavaLanguage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.ResponseBody

class TerActivity : AppCompatActivity() {
    private lateinit var bind: ActivityTerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTerBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.editor.setEditorLanguage(JavaLanguage())
        bind.terText.setOnClickListener {
            Retrofit.Builder().baseUrl("https://example.com/").addConverterFactory(GsonConverterFactory.create()).build()
                .create(Ser::class.java).get().enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            bind.editor.setText(response.body()?.string())
                        } else {
                            Toast.makeText(applicationContext, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            Toast.makeText(applicationContext, "Build from termux", Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        bind.editor?.release()
    }

    interface Ser {
        @GET("/")
        fun get(): Call<ResponseBody>
    }
}