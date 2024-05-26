package com.finals.agrifund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InputAdapter(private var campaignList: List<Data_campaigns>):
RecyclerView.Adapter<InputAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.campaign_list_image)
        val title: TextView = itemView.findViewById(R.id.display_title)
        val amount: TextView = itemView.findViewById(R.id.display_target_amt)
        val location: TextView = itemView.findViewById(R.id.display_location)
        val type: TextView = itemView.findViewById(R.id.display_type)
        val desciption: TextView = itemView.findViewById(R.id.display_description)
        val fullname: TextView = itemView.findViewById(R.id.display_fullname)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.campaign_list, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = campaignList[position]

        holder.imageView.setImageURI(item.data_img)
        holder.title.text = item.data_title
        holder.amount.text = item.data_amt.toString()
        holder.location.text = item.data_location
        holder.type.text= item.data_type
        holder.desciption.text = item.data_description
        holder.fullname.text = item.data_fullname
    }

    override fun getItemCount(): Int {
       return campaignList.size
    }
    fun updateData(newCampaignList: List<Data_campaigns>) {
        this.campaignList = newCampaignList
        notifyDataSetChanged()
    }


}