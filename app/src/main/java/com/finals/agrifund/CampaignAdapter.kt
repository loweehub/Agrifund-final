package com.finals.agrifund

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CampaignAdapter(private val campaignList: List<Data_campaigns>) : RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.campaign_list_image)
        val titleTextView: TextView = itemView.findViewById(R.id.display_title)
        val amountTextView: TextView = itemView.findViewById(R.id.display_target_amt)
        val locationTextView: TextView = itemView.findViewById(R.id.display_location)
        val typeTextView: TextView = itemView.findViewById(R.id.display_type)
        val descriptionTextView: TextView = itemView.findViewById(R.id.display_description)
        val fullnameTextView: TextView = itemView.findViewById(R.id.display_fullname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.campaign_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = campaignList[position]

        holder.titleTextView.text = currentItem.data_title
        holder.amountTextView.text = currentItem.data_amt
        holder.locationTextView.text = currentItem.data_location
        holder.typeTextView.text = currentItem.data_type
        holder.descriptionTextView.text = currentItem.data_description
        holder.fullnameTextView.text = currentItem.data_fullname

        Glide.with(holder.itemView.context)
            .load(currentItem.data_img)
            .into(holder.imageView)
    }


    override fun getItemCount() = campaignList.size
}