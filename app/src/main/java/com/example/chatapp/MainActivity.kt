package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

 class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.email.setText("a@gmail.com")
        binding.password.setText("123456")
        binding.btnJoinChat.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email/password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.checkbox.isChecked) {
                registeruser(email, password)
            } else {
                loginuser(email, password)
            }
        }
    }

    private fun loginuser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startIntent(email)
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("varun", "signInWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("varun", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun startIntent(email: String) {
        val intent = Intent(this, UsersActivity::class.java)
        intent.putExtra("senderemail", email)
        startActivity(intent)
    }

    private fun registeruser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                val userId = user?.uid
                // Store the user information in your database
                databaseReference = FirebaseDatabase.getInstance().reference
                val usersRef = databaseReference.child("users")
                userId?.let {
                    usersRef.child(it).setValue(email)
                }
                startIntent(email)
            } else {
                // User registration failed
                Log.w("varun", "signInWithEmail:failure", it.exception)
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}
