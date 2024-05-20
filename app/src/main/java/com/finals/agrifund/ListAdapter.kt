package com.finals.agrifund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter (private val homeList : List<Campaigns>) :
RecyclerView.Adapter<ListAdapter.CampaignsViewHolder>()
{




   class CampaignsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       val img :ImageView = itemView.findViewById(R.id.DashCamImg)
       val title :TextView = itemView.findViewById(R.id.DashTitle)
       val raised : TextView = itemView.findViewById(R.id.DashRaised)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homecamplist, parent, false)
        return CampaignsViewHolder(view)
    }


    override fun onBindViewHolder(holder: CampaignsViewHolder, position: Int) {
        val ovCamp = homeList[position]
        holder.img.setImageResource(ovCamp.image)
        holder.title.text = ovCamp.title
        holder.title.text = ovCamp.Raised.toString()
    }

    override fun getItemCount(): Int {
       return homeList.size
    }

}