// CampaignAdapter.kt
package com.finals.agrifund

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CampaignAdapter(
    private var campaignList: List<Data_campaigns>,
    private val listener: OnDonateButtonClickListener
) : RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {

    interface OnDonateButtonClickListener {
        fun onDonateButtonClick(campaign: Data_campaigns)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.campaign_list_image)
        val titleTextView: TextView = itemView.findViewById(R.id.display_title)
        val amountTextView: TextView = itemView.findViewById(R.id.display_target_amt)
        val locationTextView: TextView = itemView.findViewById(R.id.display_location)
        val typeTextView: TextView = itemView.findViewById(R.id.display_type)
        val descriptionTextView: TextView = itemView.findViewById(R.id.display_description)
        val fullnameTextView: TextView = itemView.findViewById(R.id.display_fullname)
        val donateButton: Button = itemView.findViewById(R.id.Donate)
        //new
        val accumulatedTextView: TextView = itemView.findViewById(R.id.display_accumulated)

        fun bind(campaign: Data_campaigns) {
            titleTextView.text = campaign.data_title
            amountTextView.text = campaign.data_amt
            locationTextView.text = campaign.data_location
            typeTextView.text = campaign.data_type
            descriptionTextView.text = campaign.data_description
            fullnameTextView.text = campaign.data_fullname
            accumulatedTextView.text = campaign.data_accumulated.toString()

            Glide.with(itemView.context)
                .load(campaign.data_img)
                .into(imageView)

            donateButton.setOnClickListener {
                listener.onDonateButtonClick(campaign)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.campaign_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(campaignList[position])
    }

    override fun getItemCount() = campaignList.size

    fun updateList(newList: List<Data_campaigns>) {
        this.campaignList = newList
        notifyDataSetChanged()
    }
}
