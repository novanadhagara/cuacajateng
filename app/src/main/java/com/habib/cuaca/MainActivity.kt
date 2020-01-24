package com.habib.cuaca

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.habib.cuaca.beranda.HomeFragment
import com.habib.cuaca.cuaca_besok.TomorrowFragment
import com.habib.cuaca.cuaca_terkini.TodayFragment
import com.habib.cuaca.infografis.InfoFragment
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.androidnetworking.AndroidNetworking


class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main, HomeFragment(), HomeFragment::class.java.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_today -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main,
                        TodayFragment(),
                        TodayFragment::class.java.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_tomorrow -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main,
                        TomorrowFragment(),
                        TomorrowFragment::class.java.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_info -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main,
                        InfoFragment(),
                        InfoFragment::class.java.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_tab)
        supportActionBar?.title = "Cuaca Jateng"
        supportActionBar?.subtitle = "Stasiun Meteorologi Ahmad Yani"
        if(savedInstanceState==null){
            navView.selectedItemId = R.id.nav_home
        }
        AndroidNetworking.initialize(applicationContext)
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Apakah anda ingin keluar?")
        builder.setPositiveButton("Ya") { _, _ ->
            //if user pressed "yes", then he is allowed to exit from application
            finish()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }
}
