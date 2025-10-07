package com.saweatherplus.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saweatherplus.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.saweatherplus.util.Security
import android.util.Base64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val btn = findViewById<Button>(R.id.btnDoRegister)

        btn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().toCharArray()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Hash password client-side before sending to Firestore (extra security layer)
            CoroutineScope(Dispatchers.Default).launch {
                val salt = Security.generateSalt()
                val hashed = Security.hashPassword(password, salt)
                val saltB = Base64.encodeToString(salt, Base64.NO_WRAP)

                // Create Firebase Auth user and save salt+hash in Firestore user doc
                auth.createUserWithEmailAndPassword(email, String(password)).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: ""
                        val userDoc = mapOf("email" to email, "salt" to saltB, "hash" to hashed)
                        firestore.collection("users").document(uid).set(userDoc)
                        Toast.makeText(this@RegisterActivity, "Registered", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Register failed: ${'$'}{task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
