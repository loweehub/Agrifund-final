package com.finals.agrifund.user
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.finals.agrifund.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val registerButton: Button = findViewById(R.id.regButton)
        registerButton.setOnClickListener {
            // Get input values
            val fullName = findViewById<EditText>(R.id.fullName).text.toString().trim()
            val phoneNo = findViewById<EditText>(R.id.phoneNo).text.toString().trim()
            val email = findViewById<EditText>(R.id.emailEditText).text.toString().trim()
            val password = findViewById<EditText>(R.id.passwordEditText).text.toString().trim()
            val confirmPass = findViewById<EditText>(R.id.confirmPass).text.toString().trim()

            // Validate input values
            if (fullName.isEmpty()) {
                Toast.makeText(this, "Full name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phoneNo.isEmpty()) {
                Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (confirmPass.isEmpty()) {
                Toast.makeText(this, "Confirm password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register the user in Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("RegisterActivity", "User registered successfully: ${user?.uid}")
                        saveUserDetailsToFirestore(user, fullName, phoneNo)
                    } else {
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
                    showSuccessDialog()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save user details: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Failed to save user details: ${e.message}")
                }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Account Created")
            .setMessage("Your account has been successfully created. You can now log in.")
            .setPositiveButton("OK") { dialog, _ -> dialog
                .dismiss()
                navigateToLogin() } .show() }

    private fun navigateToLogin() { val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Finish this activity so user can't navigate back to it
}
}