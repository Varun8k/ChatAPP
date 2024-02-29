package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityUsersBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

 class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: UsersAdapter
    private val usersList = mutableListOf<Pair<String, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        databaseReference = FirebaseDatabase.getInstance().getReference("/users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    Log.d("varun", it.value.toString())
                    val id = it.key.toString() // Get user ID
                    val email = it.value.toString() // Get user email
                    if (it.key != Firebase.auth.uid) {
                        usersList.add(Pair(email, id))
                    }
                }
                val recyclerView = binding.recyclerviewuser
                adapter = UsersAdapter(usersList) { email, id -> onuserclick(email, id) }
                recyclerView.layoutManager = LinearLayoutManager(this@UsersActivity)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("varun", "onCancelled: error")
            }
        })
    }

    fun onuserclick(email: String?, id: String?) {
        val senderemail = intent.getStringExtra("senderemail")
        val intent = Intent(this, chatActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("id", id)
        intent.putExtra("senderemail", senderemail)
        startActivity(intent)
    }
}
