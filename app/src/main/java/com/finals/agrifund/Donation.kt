package com.finals.agrifund

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class Donation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)



        val donationTitle = intent.getStringExtra("donation_title")
        val donationdescription = intent.getStringExtra("donation_description")

        // Use the donationTitle in your layout
        val titleTextView = findViewById<TextView>(R.id.title_donation)
        val descriptionTextView = findViewById<TextView>(R.id.description_donate)
        titleTextView.text = donationTitle
        descriptionTextView.text = donationdescription

        //for method
        // Set up CardView click listeners
        val gcashPayCard = findViewById<CardView>(R.id.gcash_pay)
        val visaPayCard = findViewById<CardView>(R.id.visa_pay)
        val paypalPayCard = findViewById<CardView>(R.id.paypal_pay)

        gcashPayCard.setOnClickListener { displaySelectedPaymentMethod("Gcash") }
        visaPayCard.setOnClickListener { displaySelectedPaymentMethod("Visa") }
        paypalPayCard.setOnClickListener { displaySelectedPaymentMethod("Paypal") }
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

//button pay
        val payButton = findViewById<Button>(R.id.pay_button)

        payButton.setOnClickListener {
            val msg :String? = "Thankyou"
            showCustomDialogBox(msg)
        }//end

        }

    private fun showCustomDialogBox(message: String?) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.payment_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okay = dialog.findViewById<Button>(R.id.okay_button)
        okay.setOnClickListener {
            val intent = Intent(this, Host_Layout::class.java)
            intent.putExtra("message", message)
            startActivity(intent)
        }

        dialog.show()
    }

}