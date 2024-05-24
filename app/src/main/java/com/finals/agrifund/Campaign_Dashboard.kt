package com.finals.agrifund

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Campaign_Dashboard : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InputAdapter

    companion object {
        private const val ARG_CAMPAIGN_LIST = "campaign_list"

        fun newInstance(campaignList: ArrayList<Data_campaigns>): Campaign_Dashboard {
            val fragment = Campaign_Dashboard()
            val args = Bundle()
            args.putParcelableArrayList(ARG_CAMPAIGN_LIST, campaignList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_campaign_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.campaignlist)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val campaignList = arguments?.getParcelableArrayList<Data_campaigns>(ARG_CAMPAIGN_LIST)
        adapter = InputAdapter(campaignList ?: emptyList())
        recyclerView.adapter = adapter


        //add another Button

    }
}