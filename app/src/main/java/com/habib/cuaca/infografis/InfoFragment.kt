package com.habib.cuaca.infografis


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.habib.cuaca.BuildConfig
import com.habib.cuaca.R
import com.habib.cuaca.model.Info
import org.json.JSONObject


class InfoFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    var arrayList = ArrayList<Info>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_info, container, false)
        progressBar = view.findViewById(R.id.prog)
        recyclerView = view.findViewById(R.id.rc_info)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        getInfo()
        return view
    }
    private fun getInfo(){
        AndroidNetworking.get(BuildConfig.GET_INFO)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    progressBar.visibility = View.INVISIBLE
                    val jsonArray = response?.getJSONArray("result")
                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray.getJSONObject(i)
                        arrayList.add(Info(jsonObject.getString("judul")
                            ,jsonObject.getString("gambar")
                            ,jsonObject.getString("tanggal")
                        ))
                        val adapter = AdapterInfo(arrayList,context)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter = adapter
                    }
                }
                override fun onError(anError: ANError?) {
                    Toast.makeText(context,"Koneksi Bermasalah", Toast.LENGTH_LONG).show()
                }
            })
    }
}
