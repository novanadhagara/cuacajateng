package com.habib.cuaca.cuaca_besok

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.habib.cuaca.R
import com.habib.cuaca.cuaca_terkini.DetailTodayActivity
import com.habib.cuaca.model.Kab
import kotlinx.android.synthetic.main.item_kab_besok.view.*

class AdapterBesok(private val arrayList: ArrayList<Kab>?,private val context: Context?)
    : RecyclerView.Adapter<AdapterBesok.ViewHolders>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolders {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_kab_besok,p0,false)
        return ViewHolders(view)
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.itemView.tv_kab.text = arrayList?.get(position)?.nama_kab
        holder.itemView.item_besok.setOnClickListener {
            //Toast.makeText(context, arrayList?.get(position)?.nama_kab, Toast.LENGTH_LONG).show()
            val intent = Intent(context,DetailTomorrowActivity::class.java)
            intent.putExtra("kab",arrayList?.get(position)?.nama_kab)
            context?.startActivity(intent)
        }
    }
    class ViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}