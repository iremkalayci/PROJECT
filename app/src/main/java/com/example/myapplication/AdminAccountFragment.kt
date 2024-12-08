package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminAccountFragment : Fragment(R.layout.fragment_admin_account) {

    private val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kullanıcı bilgilerini görüntüleme ve güncelleme alanları
        val tvEmail = view.findViewById<TextView>(R.id.tvAdminEmail)
        val etNewEmail = view.findViewById<EditText>(R.id.etNewAdminEmail)
        val btnUpdateInfo = view.findViewById<View>(R.id.btnUpdateAdminInfo)

        // Şifre sıfırlama alanları
        val etOldPassword = view.findViewById<EditText>(R.id.etOldAdminPassword)
        val etNewPassword = view.findViewById<EditText>(R.id.etNewAdminPassword)
        val btnResetPassword = view.findViewById<View>(R.id.btnAdminResetPassword)

        // Firebase'den kullanıcı bilgilerini al
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            tvEmail.text = "E-posta: ${user.email}"
        }

        // Kullanıcı e-posta bilgisini güncelle
        btnUpdateInfo.setOnClickListener {
            val newEmail = etNewEmail.text.toString().trim()

            if (newEmail.isNotEmpty()) {
                val userRef = db.collection("users").document(user?.uid ?: "")

                // Firebase Firestore'da kullanıcı bilgilerini güncelle
                val userMap: Map<String, Any> = hashMapOf(
                    "email" to newEmail
                )

                // Firestore'da güncelleme işlemi
                userRef.update(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "E-posta başarıyla güncellendi!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Bir hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Lütfen yeni e-posta adresini girin!", Toast.LENGTH_SHORT).show()
            }
        }

        // Şifre sıfırlama işlemi
        btnResetPassword.setOnClickListener {
            val oldPassword = etOldPassword.text.toString().trim()
            val newPassword = etNewPassword.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Şifre doğrulama ve sıfırlama işlemi
            if (user != null) {
                val credentials = EmailAuthProvider.getCredential(user.email!!, oldPassword)
                user.reauthenticate(credentials)
                    .addOnSuccessListener {
                        user.updatePassword(newPassword)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Şifre başarıyla sıfırlandı!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(requireContext(), "Şifre sıfırlanırken hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Eski şifre yanlış: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}