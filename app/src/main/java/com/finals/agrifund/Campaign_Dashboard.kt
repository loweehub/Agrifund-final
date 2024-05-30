// Campaign_Dashboard.kt
package com.finals.agrifund

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class Campaign_Dashboard : Fragment(), CampaignAdapter.OnDonateButtonClickListener {

    private lateinit var campaignRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: CampaignAdapter
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

        // Initialize adapter with listener
        adapter = CampaignAdapter(filteredCampaignList, this)
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
                    val accumulated = document.getLong("data_accumulated")?: 0
                    val documentId = document.id // Get the document ID

                    val campaignData = Data_campaigns(
                        imageUri, title, amount, location, type, description, fullname, accumulated, documentId
                    )
                    campaignList.add(campaignData)
                }

                filteredCampaignList.addAll(campaignList)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    //For searchbar
    private fun filterCampaigns(query: String?) {
        filteredCampaignList.clear()
        if (query.isNullOrEmpty()) {
            filteredCampaignList.addAll(campaignList)
        } else {
            val lowerCaseQuery = query.toLowerCase(Locale.ROOT)
            for (campaign in campaignList) {
                if (campaign.data_title.toLowerCase(Locale.ROOT).contains(lowerCaseQuery) ||
                    campaign.data_description.toLowerCase(Locale.ROOT).contains(lowerCaseQuery)
                ) {
                    filteredCampaignList.add(campaign)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    //for payment layout
    override fun onDonateButtonClick(campaign: Data_campaigns) {
        val intent = Intent(requireActivity(), Donation::class.java)
        intent.putExtra("donation_title", campaign.data_title)
        intent.putExtra("donation_description", campaign.data_description)
        intent.putExtra("campaign_id", campaign.documentId) // Pass the document ID
        // Pass additional data if needed
        startActivity(intent)
    }
}
