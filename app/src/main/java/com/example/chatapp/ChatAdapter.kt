package com.example.chatapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ChatAdapter(private var dataSet: List<chat>, private val receiverEmail: String, val senderEmail: String) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val email = view.findViewById<TextView>(R.id.user_mail)
        val msg = view.findViewById<TextView>(R.id.text_msg)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chats, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val chat = dataSet[position]

        // Check for nullability of senderId and receiverId
        val firebase = Firebase.auth.uid

        // Set email text based on senderId and receiverId
        holder.email?.text = if (chat.senderId == firebase) senderEmail else receiverEmail
        Log.d("varun", "onBindViewHolder: $receiverEmail")
        // Set message text
        holder.msg?.text = chat.message
    }
    fun updateMessages(newData: List<chat>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}
