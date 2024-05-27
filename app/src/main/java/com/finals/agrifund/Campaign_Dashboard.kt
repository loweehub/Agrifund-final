//package com.finals.agrifund
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class Campaign_Dashboard : Fragment() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: InputAdapter
//
//    companion object {
//        private const val ARG_CAMPAIGN_LIST = "campaign_list"
//
//        fun newInstance(campaignList: ArrayList<Data_campaigns>): Campaign_Dashboard {
//            val fragment = Campaign_Dashboard()
//            val args = Bundle()
//            args.putParcelableArrayList(ARG_CAMPAIGN_LIST, campaignList)
//            fragment.arguments = args
//            return fragment
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_campaign_dashboard, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        recyclerView = view.findViewById(R.id.campaignlist)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        val campaignList = arguments?.getParcelableArrayList<Data_campaigns>(ARG_CAMPAIGN_LIST)
//        adapter = InputAdapter(campaignList ?: emptyList())
//        recyclerView.adapter = adapter
//
//
//        //add another Button
//
//    }
//}
package com.finals.agrifund
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class Campaign_Dashboard : Fragment() {

    private lateinit var campaignRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_campaign_dashboard, container, false)

        campaignRecyclerView = view.findViewById(R.id.campaignlist)
        campaignRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        retrieveDataFromFirestore()

        return view
    }

    private fun retrieveDataFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val campaignsCollection = db.collection("campaigns")

        campaignsCollection.get()
            .addOnSuccessListener { documents ->
                val campaignList = mutableListOf<Data_campaigns>()

                for (document in documents) {
                    val imageUrl = document.getString("data_img") ?: ""
                    val imageUri = Uri.parse(imageUrl)
                    Log.d("Campaign_Dashboard", "Image URI: $imageUri") // Log the image URL
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

                val adapter = CampaignAdapter(campaignList)
                campaignRecyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Log.e("Firestore Error", exception.message.toString())
            }
    }
}

