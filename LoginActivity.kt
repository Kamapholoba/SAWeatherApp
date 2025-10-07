package com.saweatherplus.ui.auth

import android.content.Intent
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

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().toCharArray()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Using FirebaseAuth signInWithEmailAndPassword
            auth.signInWithEmailAndPassword(email, String(password)).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Optional: verify stored hash in Firestore
                    val uid = auth.currentUser?.uid ?: ""
                    firestore.collection("users").document(uid).get().addOnSuccessListener { doc ->
                        if (doc.exists()) {
                            val saltB = doc.getString("salt") ?: ""
                            val storedHash = doc.getString("hash") ?: ""
                            if (saltB.isNotEmpty()) {
                                CoroutineScope(Dispatchers.Default).launch {
                                    val salt = Base64.decode(saltB, Base64.NO_WRAP)
                                    val recomputed = Security.hashPassword(password, salt)
                                    runOnUiThread {
                                        if (recomputed == storedHash) {
                                            val i = Intent(this@LoginActivity, com.saweatherplus.ui.home.HomeActivity::class.java)
                                            startActivity(i)
                                            finish()
                                        } else {
                                            Toast.makeText(this@LoginActivity, "Hash mismatch (security check)", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            } else {
                                // proceed if no salt stored
                                val i = Intent(this@LoginActivity, com.saweatherplus.ui.home.HomeActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Login failed: ${'$'}{task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
