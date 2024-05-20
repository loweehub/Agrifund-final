package com.finals.agrifund

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment

class Add : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find your views here
        val button: Button? = view.findViewById(R.id.page1)
        val types = listOf("Crops", "Livestock", "Fisheries and Aquaculture", "Forestry")
        val autoComplete: AutoCompleteTextView? = view.findViewById(R.id.add_type)

        // Check if autoComplete is not null before setting the adapter
        autoComplete?.let {
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, types)
            it.setAdapter(adapter)
        }

        // Check if button is not null before setting the click listener
        button?.setOnClickListener {
            // Handle button click
            val intent = Intent(activity, Add_page_two::class.java)
            startActivity(intent)
        }
    }



}
