package com.habib.cuaca.cuaca_terkini

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.habib.cuaca.BuildConfig
import com.habib.cuaca.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class DetailTodayActivity : AppCompatActivity() {
    lateinit var kab : String
    lateinit var img : ImageView
    lateinit var prog:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_today)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        img = findViewById(R.id.img_today)
        prog = findViewById(R.id.prog)
        if(intent!=null){
            kab = intent.getStringExtra("kab")
            supportActionBar?.title = kab
            DetailKab(kab)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun DetailKab(nama:String){
        AndroidNetworking.get(BuildConfig.CARI_TERKINI+nama)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    val jsonArray = response?.getJSONArray("result")
                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val gbr = jsonObject.getString("nm_file")
                        Picasso.with(applicationContext).load(BuildConfig.IMG_TERKINI+gbr).into(img,object : Callback{
                            override fun onSuccess() {
                                prog.visibility = View.INVISIBLE
                            }
                            override fun onError() {
                            }
                        })
                    }
                }
                override fun onError(anError: ANError?) {
                    Toast.makeText(application,"Koneksi Bermasalah", Toast.LENGTH_LONG).show()
                }
            })
    }
}
