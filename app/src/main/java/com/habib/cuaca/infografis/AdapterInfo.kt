package com.habib.cuaca.infografis

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.habib.cuaca.BuildConfig
import com.habib.cuaca.R
import com.habib.cuaca.model.Info
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_info.view.*
import java.lang.Exception

class AdapterInfo(private var arrayList: ArrayList<Info>?,private var context: Context?)
    :RecyclerView.Adapter<AdapterInfo.ViewHolders>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolders {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_info,p0,false)
        return ViewHolders(view)
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.itemView.tv_judul.text = arrayList?.get(position)?.judul
        holder.itemView.tv_tgl.text = arrayList?.get(position)?.tanggal
        Picasso.with(context).load(BuildConfig.IMG_INFO+arrayList?.get(position)?.gambar).into(holder.itemView.img_info,object : Callback{
            override fun onSuccess() {
                holder.itemView.prog_info.visibility = View.INVISIBLE
            }
            override fun onError() {

            }
        })
        holder.itemView.cv_info.setOnClickListener {
            //Toast.makeText(context, arrayList?.get(position)?.gambar, Toast.LENGTH_LONG).show()
            val intent = Intent(context,DetailInfoActivity::class.java)
            intent.putExtra("tgl",arrayList?.get(position)?.tanggal)
            intent.putExtra("gbr",arrayList?.get(position)?.gambar)
            context?.startActivity(intent)
        }
    }

    class ViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}