package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class AdminUsersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_users, container, false)

        recyclerView = view.findViewById(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter

        fetchUsersFromFirestore()

        return view
    }

    private fun fetchUsersFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .addSnapshotListener(){snapshot,e->
                if(e!=null){
                    Toast.makeText(context,"veriler yuklenemedi:${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
           if(snapshot!=null){
                userList.clear()
                for (document in snapshot.documents) {
                    // id'yi Int olarak alıyoruz
                    val id = document.getLong("id")?.toInt() ?: 0 // id'yi Int'e dönüştürme
                    val nameSurname = document.getString("name_surname") ?: ""
                    val email = document.getString("email") ?: ""
                    userList.add(User(id, nameSurname, email))
                }
                userAdapter.notifyDataSetChanged()
            }

            }
    }
}