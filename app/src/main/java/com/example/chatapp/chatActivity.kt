package com.example.chatapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

 class chatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var adapter: ChatAdapter
    lateinit var senderId: String
    lateinit var receiverEmail: String
    lateinit var senderEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id").toString()
        receiverEmail = intent.getStringExtra("email").toString()
        senderEmail = intent.getStringExtra("senderemail").toString()
        senderId = Firebase.auth.uid ?: ""

        // Set up RecyclerView and adapter
        adapter = ChatAdapter(ArrayList(), receiverEmail, senderEmail)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = adapter

        // Load initial messages
        loadMessages(senderId, id)

        binding.btnsend.setOnClickListener {
            val sendmsg = binding.message.text.toString()
            if (sendmsg.isNotEmpty()) {
                sendMessage(senderId, id, sendmsg)
            }
            binding.message.setText("")
            binding.recyclerViewChat.scrollToPosition(adapter.itemCount-1)
        }

    }

    private fun loadMessages(senderId: String, receiverId: String) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val reference = databaseReference.child("/chats")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatList = ArrayList<chat>()
                snapshot.children.forEach { chatSnapshot ->
                    val chat = chatSnapshot.getValue(chat::class.java)
                    if (chat != null && (
                                chat.senderId == senderId && chat.receiverId == receiverId ||
                                        chat.senderId == receiverId && chat.receiverId == senderId
                                )) {
                        chatList.add(chat)
                    }
                }
                adapter.updateMessages(chatList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("varun", "onCancelled: chat not loaded")
            }
        })
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        val reference = FirebaseDatabase.getInstance().getReference("/chats")
        val hashMap = hashMapOf(
            "senderId" to senderId,
            "receiverId" to receiverId,
            "message" to message
        )
        reference.push().setValue(hashMap)
        loadMessages(senderId, receiverId)
    }

}

