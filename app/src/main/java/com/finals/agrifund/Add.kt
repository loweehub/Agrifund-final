package com.finals.agrifund

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class Add : Fragment() {

    private lateinit var campaignList: MutableList<Data_campaigns>

    private lateinit var image: ImageView
    private lateinit var imageUri: Uri // Declare imageUri here
    companion object {
        const val IMG_REQ_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find your views here
        val types = listOf("Donation", "Investment")
        val autoComplete: AutoCompleteTextView? = view.findViewById(R.id.add_type)
        val submit: Button = view.findViewById(R.id.submit)

        //Inputs for campaign details
        image = view.findViewById(R.id.add_image)
        val amount: EditText = view.findViewById(R.id.add_amt)
        val title: EditText = view.findViewById(R.id.add_title)
        val location: EditText = view.findViewById(R.id.add_Business_Location)
        val type: AutoCompleteTextView = view.findViewById(R.id.add_type)
        val description: EditText = view.findViewById(R.id.add_description)
        val fullname: EditText = view.findViewById(R.id.add_fullname)

        image.setOnClickListener {
            pickImageGallery()
        }

        campaignList = mutableListOf()

        // Set adapter for AutoCompleteTextView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, types)
        autoComplete?.setAdapter(adapter)

        submit.setOnClickListener {
            // Handle button click

            val amountData = amount.text.toString()
            val titleData = title.text.toString()
            val locationData = location.text.toString()
            val typeData = type.text.toString()
            val descriptionData = description.text.toString()
            val fullnameData = fullname.text.toString()

            val campaignData = Data_campaigns(
                imageUri,
                "Title: $titleData",
                amountData.toLong(),
                "Business location: $locationData",
                "Type: $typeData",
                "Description: $descriptionData",
                "Fullname: $fullnameData"
            )
            campaignList.add(campaignData)

            // Select the Campaign Dashboard navigation item
            val navigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar)
            navigationView.menu.findItem(R.id.navigation_campaigns).isChecked = true

            // Display the Campaign_Dashboard fragment
            val fragment = Campaign_Dashboard.newInstance(campaignList as ArrayList<Data_campaigns>)
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.host_main_fragment, fragment)
            fragmentTransaction.commit()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMG_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMG_REQ_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                imageUri = uri // Save the uri to a variable
                image.setImageURI(uri)
            }
        }
    }
}