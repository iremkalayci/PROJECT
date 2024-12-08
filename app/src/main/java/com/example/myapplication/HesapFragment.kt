package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HesapFragment : Fragment(R.layout.fragment_hesap) {

    private val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kullanıcı bilgilerini görüntüleme ve güncelleme alanları
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val etNewName = view.findViewById<EditText>(R.id.etNewName)
        val etNewEmail = view.findViewById<EditText>(R.id.etNewEmail)
        val btnUpdateInfo = view.findViewById<View>(R.id.btnUpdateInfo)

        // Şifre sıfırlama alanları
        val etOldPassword = view.findViewById<EditText>(R.id.etOldPassword)
        val etNewPassword = view.findViewById<EditText>(R.id.etNewPassword)
        val btnResetPassword = view.findViewById<View>(R.id.btnResetPassword)

        // Üyeliği pasif etme alanı
        val btnDeactivate = view.findViewById<View>(R.id.btnDeactivate)

        // Firebase'den kullanıcı bilgilerini al
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            tvName.text = "Ad:İrem Kalaycı"
            tvEmail.text = "E-posta: ${user.email}"
        }

        // Kullanıcı bilgilerini güncelle
        btnUpdateInfo.setOnClickListener {
            val newName = etNewName.text.toString().trim()
            val newEmail = etNewEmail.text.toString().trim()

            if (newName.isNotEmpty() && newEmail.isNotEmpty()) {
                val userRef = db.collection("users").document(user?.uid ?: "")

                // Firebase Firestore'da kullanıcı bilgilerini güncelle
                val userMap: Map<String, Any> = hashMapOf(
                    "name" to newName,
                    "email" to newEmail
                )

                // Firestore'da güncelleme işlemi
                userRef.update(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Bilgiler başarıyla güncellendi!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Bir hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
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

        // Üyeliği pasif etme işlemi
        btnDeactivate.setOnClickListener {
            user?.delete()
                ?.addOnSuccessListener {
                    Toast.makeText(requireContext(), "Üyelik pasif edildi. Çıkış yapılıyor...", Toast.LENGTH_SHORT).show()

                    // Login selection ekranına yönlendirme
                    val intent = Intent(requireContext(), LoginSelectionActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                ?.addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Üyelik silinemedi: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}