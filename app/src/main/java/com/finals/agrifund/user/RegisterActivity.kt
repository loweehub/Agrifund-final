package com.finals.agrifund.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.finals.agrifund.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

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

            // If all validations pass, navigate back to login
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish this activity so user can't navigate back to it
        }
    }
}
