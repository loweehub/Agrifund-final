//package com.finals.agrifund
//
//import android.app.Dialog
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.view.Window
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.cardview.widget.CardView
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.bumptech.glide.Glide
//import com.google.firebase.firestore.FirebaseFirestore
//
//class Donation : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_donation)
//
//        val donationTitle = intent.getStringExtra("donation_title")
//        val donationdescription = intent.getStringExtra("donation_description")
//
//        // Use the donationTitle in your layout
//        val titleTextView = findViewById<TextView>(R.id.title_donation)
//        val descriptionTextView = findViewById<TextView>(R.id.description_donate)
//        titleTextView.text = donationTitle
//        descriptionTextView.text = donationdescription
//
//        //for method
//        // Set up CardView click listeners
//        val gcashPayCard = findViewById<CardView>(R.id.gcash_pay)
//        val visaPayCard = findViewById<CardView>(R.id.visa_pay)
//        val paypalPayCard = findViewById<CardView>(R.id.paypal_pay)
//
//        gcashPayCard.setOnClickListener { displaySelectedPaymentMethod("Gcash") }
//        visaPayCard.setOnClickListener { displaySelectedPaymentMethod("Visa") }
//        paypalPayCard.setOnClickListener { displaySelectedPaymentMethod("Paypal") }
//    }
//
//    private fun displaySelectedPaymentMethod(method: String) {
//        val paymentMethodContainer = findViewById<LinearLayout>(R.id.payment_method_container)
//        paymentMethodContainer.removeAllViews()
//
//        val methodTextView = TextView(this).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            text = method
//            textSize = 25f
//            setTextColor(resources.getColor(R.color.white))
//            textAlignment = View.TEXT_ALIGNMENT_CENTER
//        }
//
//        paymentMethodContainer.addView(methodTextView)
//
////button pay
//        val payButton = findViewById<Button>(R.id.pay_button)
//
//        payButton.setOnClickListener {
//            //updating accumulated balance
//            val input = accumulatedValue.text.toString().toLongOrNull() ?: 0 // Get input from EditText
//            updateAccumulatedData(input) // Update accumulated data in Firestore
//
//            //showing success dialog
//            val msg :String? = "Thankyou"
//            showCustomDialogBox(msg)
//        }//end
//
//        }
//    //for input
//    val accumulatedValue = findViewById<EditText>(R.id.accumulated_input)
//    private fun showCustomDialogBox(message: String?) {
//
//
//
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.payment_dialog)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        val okay = dialog.findViewById<Button>(R.id.okay_button)
//        okay.setOnClickListener {
//            val intent = Intent(this, Host_Layout::class.java)
//            intent.putExtra("message", message)
//            startActivity(intent)
//        }
//
//        dialog.show()
//    }
//
//    // For incrementing data accumulated
//    private fun updateAccumulatedData(amount: Long) {
//        // Update the accumulated data in Firestore
//        val firestore = FirebaseFirestore.getInstance()
//        val accumulatedDataRef = firestore.collection("accumulated_data").document("total")
//
//        accumulatedDataRef.get()
//            .addOnSuccessListener { documentSnapshot ->
//                if (documentSnapshot.exists()) {
//                    // If the document exists, update the accumulated data
//                    val currentAmount = documentSnapshot.getLong("amount") ?: 0
//                    accumulatedDataRef.update("amount", currentAmount + amount)
//                } else {
//                    // If the document doesn't exist, create it with the initial amount
//                    val newAccumulatedData = hashMapOf(
//                        "amount" to amount
//                    )
//                    accumulatedDataRef.set(newAccumulatedData)
//                }
//            }
//            .addOnFailureListener { e ->
//
//            }
//
//
//
//}
//}
package com.finals.agrifund

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class Donation : AppCompatActivity() {
    private lateinit var accumulatedValue: EditText
    private val firestore = FirebaseFirestore.getInstance()
    private var campaignId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)

        val donationTitle = intent.getStringExtra("donation_title")
        val donationDescription = intent.getStringExtra("donation_description")
  //get document Id
        campaignId = intent.getStringExtra("campaign_id")


        // Use the donationTitle in your layout
        val titleTextView = findViewById<TextView>(R.id.title_donation)
        val descriptionTextView = findViewById<TextView>(R.id.description_donate)
        titleTextView.text = donationTitle
        descriptionTextView.text = donationDescription

        // Set up CardView click listeners
        val gcashPayCard = findViewById<CardView>(R.id.gcash_pay)
        val visaPayCard = findViewById<CardView>(R.id.visa_pay)
        val paypalPayCard = findViewById<CardView>(R.id.paypal_pay)

        gcashPayCard.setOnClickListener { displaySelectedPaymentMethod("Gcash") }
        visaPayCard.setOnClickListener { displaySelectedPaymentMethod("Visa") }
        paypalPayCard.setOnClickListener { displaySelectedPaymentMethod("Paypal") }

        // Initialize the input field
        accumulatedValue = findViewById(R.id.accumulated_input)

        // Set up the pay button listener
        val payButton = findViewById<Button>(R.id.pay_button)
        payButton.setOnClickListener {
            // Get input from EditText
            val input = accumulatedValue.text.toString().toLong()
            // Update accumulated data in Firestore
            updateAccumulatedData(input)
            showCustomDialogBox("Thank you")

        }
    }

    private fun displaySelectedPaymentMethod(method: String) {
        val paymentMethodContainer = findViewById<LinearLayout>(R.id.payment_method_container)
        paymentMethodContainer.removeAllViews()

        val methodTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = method
            textSize = 25f
            setTextColor(resources.getColor(R.color.white))
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        paymentMethodContainer.addView(methodTextView)
    }

    private fun showCustomDialogBox(message: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.payment_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //jumping to campaign_dashboard
        val okay = dialog.findViewById<Button>(R.id.okay_button)

        okay.setOnClickListener {
            val intent = Intent(this, Host_Layout::class.java)
            intent.putExtra("message", message)
            startActivity(intent)
        }

        dialog.show()
    }

    // For incrementing data accumulation
    private fun updateAccumulatedData(amount: Long) {
        campaignId?.let { id ->
            val accumulatedDataRef = firestore.collection("campaigns").document(id)

            accumulatedDataRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // If the document exists, update the accumulated data
                        val currentAmount = documentSnapshot.getLong("data_accumulated") ?: 0
                        accumulatedDataRef.update("data_accumulated", currentAmount + amount)
                            .addOnSuccessListener {
                                // Show success dialog after updating the data

                            }
                            .addOnFailureListener { e ->
                                Log.e("FirestoreError", "Error updating accumulated data: $e")
                            }
                    } else {
                        // If the document doesn't exist, create it with the initial amount
                        val newAccumulatedData = hashMapOf(
                            "data_accumulated" to amount
                        )
                        accumulatedDataRef.set(newAccumulatedData)
                            .addOnSuccessListener {
                                // Show success dialog after creating the document
                                showCustomDialogBox("Thank you")
                            }
                            .addOnFailureListener { e ->
                                Log.e("FirestoreError", "Error creating accumulated data: $e")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error fetching accumulated data: $e")
                }
        }
    }
}
