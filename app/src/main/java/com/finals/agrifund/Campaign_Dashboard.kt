package com.finals.agrifund

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.finals.agrifund.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class Campaign_Dashboard : Fragment() {

    private lateinit var campaignRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
 lateinit var adapter: CampaignAdapter
    private var campaignList = mutableListOf<Data_campaigns>()
    private var filteredCampaignList = mutableListOf<Data_campaigns>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_campaign_dashboard, container, false)

        campaignRecyclerView = view.findViewById(R.id.campaignlist)
        campaignRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCampaigns(newText)
                return true
            }
        })

        retrieveDataFromFirestore()

        // Initialize adapter here
        adapter = CampaignAdapter(filteredCampaignList)
        campaignRecyclerView.adapter = adapter

        return view
    }


    private fun retrieveDataFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val campaignsCollection = db.collection("campaigns")

        campaignsCollection.get()
            .addOnSuccessListener { documents ->
                campaignList.clear()
                for (document in documents) {
                    val imageUrl = document.getString("data_img") ?: ""
                    val imageUri = if (imageUrl.isNotEmpty()) Uri.parse(imageUrl) else Uri.EMPTY
                    val title = document.getString("data_title") ?: ""
                    val amount = document.getString("data_amt") ?: ""
                    val location = document.getString("data_location") ?: ""
                    val type = document.getString("data_type") ?: ""
                    val description = document.getString("data_description") ?: ""
                    val fullname = document.getString("data_fullname") ?: ""

                    val campaignData = Data_campaigns(imageUri, title,
                        amount, location, type, description, fullname)
                    campaignList.add(campaignData)
                }

                filteredCampaignList.addAll(campaignList)
                adapter = CampaignAdapter(filteredCampaignList)
                campaignRecyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    private fun filterCampaigns(query: String?) {
        filteredCampaignList.clear()
        if (query.isNullOrEmpty()) {
            filteredCampaignList.addAll(campaignList)
        } else {
            val lowerCaseQuery = query.toLowerCase(Locale.ROOT)
            for (campaign in campaignList) {
                if (campaign.data_title.toLowerCase(Locale.ROOT).contains(lowerCaseQuery) ||
                    campaign.data_description.toLowerCase(Locale.ROOT).contains(lowerCaseQuery)) {
                    filteredCampaignList.add(campaign)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }
}
