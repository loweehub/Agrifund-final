//package com.finals.agrifund
//import android.app.AlertDialog
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import androidx.cardview.widget.CardView
//import androidx.fragment.app.Fragment
//import com.finals.agrifund.user.LoginActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.UserInfo
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//import com.squareup.picasso.Picasso
//import java.io.IOException
//import java.util.*
//
//class Profile : Fragment() {
//    private lateinit var auth: FirebaseAuth
//    private lateinit var firestore: FirebaseFirestore
//    private lateinit var storage: FirebaseStorage
//    private lateinit var profileImageView: ImageView
//    private lateinit var nameTextView: TextView
//    private lateinit var emailTextView: TextView
//
//    private val PICK_IMAGE_REQUEST = 71
//    private var filePath: Uri? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//
//        // Initialize Firebase Auth, Firestore, and Storage
//        auth = FirebaseAuth.getInstance()
//        firestore = FirebaseFirestore.getInstance()
//        storage = FirebaseStorage.getInstance()
//
//        // Get current user
//        val currentUser = auth.currentUser
//
//        // Find views
//        profileImageView = view.findViewById(R.id.profile_picture)
//        nameTextView = view.findViewById(R.id.profile_name)
//        emailTextView = view.findViewById(R.id.profile_email)
//        val uploadBtn = view.findViewById<Button>(R.id.uploadBtn)
//        val subsBtn = view.findViewById<CardView>(R.id.subsBtn)
//
//        // Set user data
//        currentUser?.let { user ->
//            emailTextView.text = user.email
//            // Check if the user is signed in with Google
//            val isGoogleUser = isGoogleSignIn(user)
//            if (isGoogleUser) {
//                nameTextView.text = user.displayName
//                Picasso.get().load(user.photoUrl).into(profileImageView)
//                uploadBtn.visibility = View.GONE // Hide upload button for Google Sign-In users
//            } else {
//                fetchProfileData(user)
//                // Set click listener for the upload button to select an image
//                uploadBtn.setOnClickListener {
//                    chooseImage()
//                }
//            }
//        }
//
//        // Set click listener for subsBtn to display the dialog
//        subsBtn.setOnClickListener {
//            currentUser?.let { user ->
//                showPersonalInfoDialog(user)
//            }
//        }
//
//        // Find the notification Button
//        val notifButton = view.findViewById<CardView>(R.id.notifButton)
//        // Set click listener for the notification Button
//        notifButton.setOnClickListener {
//            // Navigate to the Notification activity
//            startActivity(Intent(activity, Notification::class.java))
//        }
//
//        // Find the logout Button
//        val logoutBtn = view.findViewById<CardView>(R.id.logoutBtn)
//        // Set click listener for the logout Button
//        logoutBtn.setOnClickListener {
//            // Call the logout function
//            logout()
//        }
//
//        return view
//    }
//
//    private fun isGoogleSignIn(user: FirebaseUser): Boolean {
//        for (userInfo: UserInfo in user.providerData) {
//            if (userInfo.providerId == "google.com") {
//                return true
//            }
//        }
//        return false
//    }
//
//    private fun fetchProfileData(user: FirebaseUser) {
//        firestore.collection("users").document(user.uid).get()
//            .addOnSuccessListener { document ->
//                if (document != null && document.exists()) {
//                    val fullName = document.getString("fullName")
//                    val profileImageUrl = document.getString("profileImageUrl")
//                    val phoneNumber = document.getString("phoneNo")
//                    nameTextView.text = fullName ?: "No Name Found"
//                    emailTextView.text = user.email ?: "No Email Found"
//                    profileImageUrl?.let {
//                        Picasso.get().load(profileImageUrl).into(profileImageView)
//                    }
//                } else {
//                    nameTextView.text = "No Name Found"
//                    emailTextView.text = user.email ?: "No Email Found"
//                }
//            }
//            .addOnFailureListener { exception ->
//                nameTextView.text = "Failed to load name"
//                emailTextView.text = "Failed to load email"
//            }
//    }
//
//    private fun showPersonalInfoDialog(user: FirebaseUser) {
//        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_personal_info, null)
//        val fullNameTextView = dialogView.findViewById<TextView>(R.id.fullNameTextView)
//        val emailTextView = dialogView.findViewById<TextView>(R.id.emailTextView)
//        val phoneTextView = dialogView.findViewById<TextView>(R.id.phoneTextView)
//        val fullNameEditText = dialogView.findViewById<EditText>(R.id.fullNameEditText)
//        val emailEditText = dialogView.findViewById<EditText>(R.id.emailEditText)
//        val phoneEditText = dialogView.findViewById<EditText>(R.id.phoneEditText)
//        val editButton = dialogView.findViewById<Button>(R.id.editButton)
//        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)
//
//        // Fetch user data from Firestore and display
//        firestore.collection("users").document(user.uid).get()
//            .addOnSuccessListener { document ->
//                if (document != null && document.exists()) {
//                    val fullName = document.getString("fullName") ?: "No Name Found"
//                    val email = user.email ?: "No Email Found"
//                    val phoneNumber = document.getString("phoneNo") ?: "No Phone Number"
//
//                    fullNameTextView.text = fullName
//                    emailTextView.text = email
//                    phoneTextView.text = phoneNumber
//
//                    fullNameEditText.setText(fullName)
//                    emailEditText.setText(email)
//                    phoneEditText.setText(phoneNumber)
//                }
//            }
//
//        val alertDialog = AlertDialog.Builder(activity)
//            .setView(dialogView)
//            .setTitle("Personal Information")
//            .create()
//
//        editButton.setOnClickListener {
//            fullNameTextView.visibility = View.GONE
//            emailTextView.visibility = View.GONE
//            phoneTextView.visibility = View.GONE
//            fullNameEditText.visibility = View.VISIBLE
//            emailEditText.visibility = View.VISIBLE
//            phoneEditText.visibility = View.VISIBLE
//            saveButton.visibility = View.VISIBLE
//        }
//
//        saveButton.setOnClickListener {
//            val updatedFullName = fullNameEditText.text.toString()
//            val updatedEmail = emailEditText.text.toString()
//            val updatedPhone = phoneEditText.text.toString()
//
//            val userUpdates = mapOf(
//                "fullName" to updatedFullName,
//                "email" to updatedEmail,
//                "phoneNo" to updatedPhone // Update the phone number field
//            )
//
//            firestore.collection("users").document(user.uid).update(userUpdates)
//                .addOnSuccessListener {
//                    Toast.makeText(activity, "Information updated", Toast.LENGTH_SHORT).show()
//                    alertDialog.dismiss()
//                    fetchProfileData(user) // Refresh the profile data
//                }
//                .addOnFailureListener {
//                    Toast.makeText(activity, "Failed to update information", Toast.LENGTH_SHORT).show()
//                }
//        }
//
//        alertDialog.show()
//    }
//
//    private fun chooseImage() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
//    }
//
//    private fun logout() {
//        FirebaseAuth.getInstance().signOut()
//        val intent = Intent(activity, LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        activity?.finish()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
//            filePath = data.data
//            try {
//                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
//                profileImageView.setImageBitmap(bitmap)
//                uploadImage()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun uploadImage() {
//        if (filePath != null) {
//            val ref = storage.reference.child("profile_images/${UUID.randomUUID()}")
//            ref.putFile(filePath!!)
//                .addOnSuccessListener {
//                    ref.downloadUrl.addOnSuccessListener { uri ->
//                        val user = auth.currentUser
//                        user?.let {
//                            firestore.collection("users").document(it.uid)
//                                .update("profileImageUrl", uri.toString())
//                                .addOnSuccessListener {
//                                    Toast.makeText(activity, "Image Uploaded", Toast.LENGTH_SHORT).show()
//                                }
//                                .addOnFailureListener { e ->
//                                    Toast.makeText(activity, "Failed to upload image", Toast.LENGTH_SHORT).show()
//                                }
//                        }
//                    }
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(activity, "Failed to upload image", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }
//}
//
package com.finals.agrifund

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.finals.agrifund.user.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.IOException
import java.util.*

class Profile : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private val TAG = "ProfileFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize Firebase Auth, Firestore, and Storage
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        // Get current user
        val currentUser = auth.currentUser

        // Find views
        profileImageView = view.findViewById(R.id.profile_picture)
        nameTextView = view.findViewById(R.id.profile_name)
        emailTextView = view.findViewById(R.id.profile_email)
        val uploadBtn = view.findViewById<Button>(R.id.uploadBtn)
        val subsBtn = view.findViewById<CardView>(R.id.subsBtn)

        // Set user data
        currentUser?.let { user ->
            emailTextView.text = user.email
            // Check if the user is signed in with Google
            val isGoogleUser = isGoogleSignIn(user)
            if (isGoogleUser) {
                nameTextView.text = user.displayName
                // Save Google user data to Firestore if not already saved
                saveGoogleUserDataToFirestore(user)
                // Fetch Google user data from Firestore
                fetchProfileData(user)
                // Hide upload button for Google Sign-In users
                uploadBtn.visibility = View.GONE
            } else {
                fetchProfileData(user)
                // Set click listener for the upload button to select an image
                uploadBtn.setOnClickListener {
                    chooseImage()
                }
            }
        }

        // Set click listener for subsBtn to display the dialog
        subsBtn.setOnClickListener {
            currentUser?.let { user ->
                showPersonalInfoDialog(user)
            }
        }

        // Find the notification Button
        val notifButton = view.findViewById<CardView>(R.id.notifButton)
        // Set click listener for the notification Button
        notifButton.setOnClickListener {
            // Navigate to the Notification activity
            startActivity(Intent(activity, Notification::class.java))
        }

        // Find the logout Button
        val logoutBtn = view.findViewById<CardView>(R.id.logoutBtn)
        // Set click listener for the logout Button
        logoutBtn.setOnClickListener {
            // Call the logout function
            logout()
        }

        return view
    }

    private fun isGoogleSignIn(user: FirebaseUser): Boolean {
        for (userInfo: UserInfo in user.providerData) {
            if (userInfo.providerId == "google.com") {
                return true
            }
        }
        return false
    }

    private fun fetchProfileData(user: FirebaseUser) {
        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val fullName = document.getString("fullName")
                    val profileImageUrl = document.getString("profileImageUrl")
                    val phoneNumber = document.getString("phoneNo")
                    nameTextView.text = fullName ?: "No Name Found"
                    emailTextView.text = user.email ?: "No Email Found"
                    profileImageUrl?.let {
                        Picasso.get().load(profileImageUrl).into(profileImageView)
                    }
                } else {
                    nameTextView.text = "No Name Found"
                    emailTextView.text = user.email ?: "No Email Found"
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching profile data", exception)
                nameTextView.text = "Failed to load name"
                emailTextView.text = "Failed to load email"
            }
    }

    private fun saveGoogleUserDataToFirestore(user: FirebaseUser) {
        // Check if Google user data is already saved
        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (document == null || !document.exists()) {
                    // Save Google user data to Firestore
                    val userData = hashMapOf(
                        "fullName" to user.displayName,
                        "email" to user.email,
                        "profileImageUrl" to user.photoUrl.toString(),
                        "phoneNo" to "" // Set phone number to empty for now
                    )
                    firestore.collection("users").document(user.uid)
                        .set(userData)
                        .addOnSuccessListener {
                            Log.d(TAG, "Google user data saved to Firestore")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error saving Google user data to Firestore", e)
                        }
                }
            }
    }

    private fun showPersonalInfoDialog(user: FirebaseUser) {
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_personal_info, null)
        val fullNameTextView = dialogView.findViewById<TextView>(R.id.fullNameTextView)
        val emailTextView = dialogView.findViewById<TextView>(R.id.emailTextView)
        val phoneTextView = dialogView.findViewById<TextView>(R.id.phoneTextView)
        val fullNameEditText = dialogView.findViewById<EditText>(R.id.fullNameEditText)
        val emailEditText = dialogView.findViewById<EditText>(R.id.emailEditText)
        val phoneEditText = dialogView.findViewById<EditText>(R.id.phoneEditText)
        val editButton = dialogView.findViewById<Button>(R.id.editButton)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        // Fetch user data from Firestore and display
        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val fullName = document.getString("fullName") ?: "No Name Found"
                    val email = user.email ?: "No Email Found"
                    val phoneNumber = document.getString("phoneNo") ?: "No Phone Number"

                    fullNameTextView.text = fullName
                    emailTextView.text = email
                    phoneTextView.text = phoneNumber

                    fullNameEditText.setText(fullName)
                    emailEditText.setText(email)
                    phoneEditText.setText(phoneNumber)
                }
            }

        val alertDialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setTitle("Personal Information")
            .create()

        editButton.setOnClickListener {
            fullNameTextView.visibility = View.GONE
            emailTextView.visibility = View.GONE
            phoneTextView.visibility = View.GONE
            fullNameEditText.visibility = View.VISIBLE
            emailEditText.visibility = View.VISIBLE
            phoneEditText.visibility = View.VISIBLE
            saveButton.visibility = View.VISIBLE
        }

        saveButton.setOnClickListener {
            val updatedFullName = fullNameEditText.text.toString()
            val updatedEmail = emailEditText.text.toString()
            val updatedPhone = phoneEditText.text.toString()

            val userUpdates = mapOf(
                "fullName" to updatedFullName,
                "email" to updatedEmail,
                "phoneNo" to updatedPhone // Update the phone number field
            )

            firestore.collection("users").document(user.uid).update(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(activity, "Information updated", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                    fetchProfileData(user) // Refresh the profile data
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Failed to update information", Toast.LENGTH_SHORT).show()
                }
        }

        alertDialog.show()
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
                profileImageView.setImageBitmap(bitmap)
                uploadImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storage.reference.child("profile_images/${UUID.randomUUID()}")
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val user = auth.currentUser
                        user?.let {
                            firestore.collection("users").document(it.uid)
                                .update("profileImageUrl", uri.toString())
                                .addOnSuccessListener {
                                    Toast.makeText(activity, "Image Uploaded", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(activity, "Failed to upload image", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
