package com.finals.agrifund

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter_Campaigns(private val items: List<Data_Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val View_TypeA = 1
        private const val View_TypeB = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Data_Item.Details -> View_TypeA
            is Data_Item.User_Info -> View_TypeB
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            View_TypeA -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add, parent, false)
                ViewHolderTypeA(view)
            }
            View_TypeB -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_add_page_two, parent, false)
                ViewHolderTypeB(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is Data_Item.Details -> (holder as ViewHolderTypeA).bind(item)
            is Data_Item.User_Info -> (holder as ViewHolderTypeB).bind(item)
        }
    }

    inner class ViewHolderTypeA(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.add_image)
        private val title: EditText = itemView.findViewById(R.id.add_title)
        private val amount: EditText = itemView.findViewById(R.id.add_amt)
        private val location: EditText = itemView.findViewById(R.id.add_Business_Location)
        private val type: AutoCompleteTextView = itemView.findViewById(R.id.add_type)
        private val description: EditText = itemView.findViewById(R.id.add_description)

        fun bind(item: Data_Item.Details) {
            img.setImageResource(item.data_img)
            title.setText(item.data_title)
            amount.setText(item.data_amt.toString())
            location.setText(item.data_location)
            type.setText(item.data_type, false) // 'false' to avoid triggering autocomplete dropdown
            description.setText(item.data_description)
        }
    }

    inner class ViewHolderTypeB(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lastname: TextView = itemView.findViewById(R.id.add_lastname)
        private val firstname: TextView = itemView.findViewById(R.id.add_firstname)
        private val occupation: TextView = itemView.findViewById(R.id.add_occupation)
        private val address: TextView = itemView.findViewById(R.id.add_addres)

        fun bind(item: Data_Item.User_Info) {
            lastname.text = item.user_lastname
            firstname.text = item.user_firstname
            occupation.text = item.user_occupation
            address.text = item.user_address
        }
    }
}