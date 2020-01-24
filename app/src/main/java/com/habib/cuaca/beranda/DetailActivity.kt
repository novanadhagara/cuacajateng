package com.habib.cuaca.beranda

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.habib.cuaca.BuildConfig
import com.habib.cuaca.R
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var judul:String
    private lateinit var ket : String
    private lateinit var gambar:String
    private lateinit var tmt : String
    private lateinit var tgl : String
    lateinit var jdl : TextView
    lateinit var kt : TextView
    lateinit var gbr: ImageView
    lateinit var tmp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Peringatan Dini"
        val intent = intent
        judul = intent.getStringExtra("judul")
        ket = intent.getStringExtra("ket")
        gambar = intent.getStringExtra("gambar")
        tmt = intent.getStringExtra("tempat")
        tgl = intent.getStringExtra("tanggal")

        jdl = findViewById(R.id.d_judul)
        kt = findViewById(R.id.d_ket)
        gbr = findViewById(R.id.d_img)
        tmp = findViewById(R.id.d_tgl)

        jdl.text = judul
        kt.text = ket
        Picasso.with(applicationContext).load(BuildConfig.IMG_DINI+gambar).into(gbr)
        tmp.text = "$tmt, $tgl"
        gbr.setOnClickListener {
            val inte = Intent(this,DetailFullActivity::class.java)
            inte.putExtra("gbr",gambar)
            startActivity(inte)
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
}
