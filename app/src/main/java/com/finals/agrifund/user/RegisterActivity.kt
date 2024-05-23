package com.finals.agrifund.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.finals.agrifund.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Handle window insets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the register button
        val registerButton: Button = findViewById(R.id.regButton)

        // Set click listener for the register button
        registerButton.setOnClickListener {
            val fullNameEditText: EditText = findViewById(R.id.fullName)
            val phoneNoEditText: EditText = findViewById(R.id.phoneNo)
            val emailEditText: EditText = findViewById(R.id.emailEditText)
            val passwordEditText: EditText = findViewById(R.id.passwordEditText)
            val confirmPassEditText: EditText = findViewById(R.id.confirmPass)

            val fullName = fullNameEditText.text.toString().trim()
            val phoneNo = phoneNoEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPass = confirmPassEditText.text.toString().trim()

            // Validate input fields
            if (fullName.isEmpty()) {
                fullNameEditText.error = "Full Name is required"
                fullNameEditText.requestFocus()
                return@setOnClickListener
            }

            if (phoneNo.isEmpty()) {
                phoneNoEditText.error = "Phone Number is required"
                phoneNoEditText.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                emailEditText.error = "Email is required"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Enter a valid email"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Password is required"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                passwordEditText.error = "Password should be at least 6 characters long"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            if (confirmPass.isEmpty()) {
                confirmPassEditText.error = "Confirm Password is required"
                confirmPassEditText.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPass) {
                confirmPassEditText.error = "Passwords do not match"
                confirmPassEditText.requestFocus()
                return@setOnClickListener
            }

            // Register the user in Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful, save additional user details to Firestore
                        val user = auth.currentUser
                        Log.d("RegisterActivity", "User registered successfully: ${user?.uid}")
                        saveUserDetailsToFirestore(user, fullName, phoneNo)
                    } else {
                        // If registration fails, display a message to the user
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        Log.e("RegisterActivity", "Registration failed: ${task.exception?.message}")
                    }
                }
        }
    }

    private fun saveUserDetailsToFirestore(user: FirebaseUser?, fullName: String, phoneNo: String) {
        user?.let {
            val userDetails = hashMapOf(
                "uid" to user.uid,
                "fullName" to fullName,
                "phoneNo" to phoneNo,
                "email" to user.email
            )

            firestore.collection("users").document(user.uid)
                .set(userDetails)
                .addOnSuccessListener {
                    Log.d("RegisterActivity", "User details saved successfully")
                    runOnUiThread {
                        showSuccessDialog()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save user details: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Failed to save user details: ${e.message}")
                }
        }
    }

    private fun showSuccessDialog() {
        Log.d("RegisterActivity", "Showing success dialog")
        runOnUiThread {
            AlertDialog.Builder(this)
                .setTitle("Account Created")
                .setMessage("Your account has been successfully created.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    navigateToLogin()
                }
                .show()
        }
    }

    private fun navigateToLogin() {
        Log.d("RegisterActivity", "Navigating to login")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Finish this activity so user can't navigate back to it
    }
}
