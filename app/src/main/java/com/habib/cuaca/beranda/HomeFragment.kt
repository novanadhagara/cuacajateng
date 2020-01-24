package com.habib.cuaca.beranda


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.habib.cuaca.BuildConfig
import com.habib.cuaca.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import org.json.JSONObject
import java.lang.Exception


class HomeFragment : Fragment() {
    lateinit var carouselView:CarouselView
    lateinit var judul : TextView
    lateinit var gbr : ImageView
    lateinit var ket : TextView
    lateinit var tmp : TextView
    lateinit var prog : ProgressBar
    lateinit var card : CardView
    private val images = arrayOf(
        R.drawable.gbr_1,
        R.drawable.gbr_2,
        R.drawable.gbr_3,
        R.drawable.gbr_4,
        R.drawable.gbr_5
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        carouselView = view.findViewById(R.id.carouselView)
        carouselView.pageCount = images.count()
        carouselView.setImageListener(imageListener)
        judul = view.findViewById(R.id.judul_dini)
        gbr  = view.findViewById(R.id.img_dini)
        ket = view.findViewById(R.id.ket_dini)
        tmp = view.findViewById(R.id.tmp_dini)
        prog = view.findViewById(R.id.prog)
        card = view.findViewById(R.id.cv_dini)
        getDini()
        return view
    }
    private fun getDini(){
        AndroidNetworking.get(BuildConfig.GET_DINI)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    val jsonArray = response?.getJSONArray("result")
                    for(i in 0 until jsonArray!!.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val jdl = jsonObject.getString("judul")
                        val kete= jsonObject.getString("ket")
                        val tmt = jsonObject.getString("tempat")
                        val tgl = jsonObject.getString("tanggal")
                        val gb = jsonObject.getString("gambar")
                        judul.text = jdl
                        ket.text = kete
                        tmp.text = "$tmt, $tgl"
                        Picasso.with(context).load(BuildConfig.IMG_DINI+gb).into(gbr,object : Callback{
                            override fun onSuccess() {
                                prog.visibility = View.INVISIBLE
                                card.visibility = View.VISIBLE
                            }
                            override fun onError() {

                            }
                        })
                        card.setOnClickListener {
                            //Toast.makeText(context,"$jdl",Toast.LENGTH_LONG).show()
                            val intent = Intent(context,DetailActivity::class.java)
                            intent.putExtra("judul",jdl)
                            intent.putExtra("ket",kete)
                            intent.putExtra("tempat",tmt)
                            intent.putExtra("tanggal",tgl)
                            intent.putExtra("gambar",gb)
                            startActivity(intent)
                        }
                    }
                }
                override fun onError(anError: ANError?) {
                    Toast.makeText(context,"Koneksi Bermasalah",Toast.LENGTH_LONG).show()
                }

            })
    }
    private var imageListener: ImageListener = ImageListener {
            position, imageView -> imageView.setImageResource(images[position])
    }
}
