package com.habib.cuaca.cuaca_besok


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.habib.cuaca.BuildConfig
import com.habib.cuaca.R
import com.habib.cuaca.model.Kab
import org.json.JSONObject
import java.util.ArrayList


class TomorrowFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    var arrayList = ArrayList<Kab>()
    lateinit var search:SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tomorrow, container, false)
        progressBar = view.findViewById(R.id.prog)
        recyclerView = view.findViewById(R.id.rv_besok)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        getKab()
        setHasOptionsMenu(true)
        return view
    }
    private fun getKab(){
        AndroidNetworking.get(BuildConfig.GET_KAB)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    progressBar.visibility = View.INVISIBLE
                    val jsonArray = response?.getJSONArray("result")
                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray.getJSONObject(i)
                        arrayList.add(Kab(jsonObject.getString("nm_kab")))
                        val adapter = AdapterBesok(arrayList,context)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter = adapter
                    }
                }
                override fun onError(anError: ANError?) {
                    Toast.makeText(context,"Koneksi Bermasalah", Toast.LENGTH_LONG).show()
                }
            })
    }
    private fun CariKab(nama: String?){
        AndroidNetworking.get(BuildConfig.CARI_BESOK+nama)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    //progressBar.visibility = View.INVISIBLE
                    val jsonArray = response?.getJSONArray("result")
                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray.getJSONObject(i)
                        arrayList.add(Kab(jsonObject.getString("nm_kab")))
                        val adapter = AdapterBesok(arrayList,context)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter = adapter
                    }
                }
                override fun onError(anError: ANError?) {
                }
            })
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.dashboard,menu)
        val searchItem = menu?.findItem(R.id.action_search)
        search = searchItem?.actionView as SearchView
        search.queryHint = "Cari Daerah"
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)){
                    getKab()
                }else{
                    CariKab(newText)
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }
}
