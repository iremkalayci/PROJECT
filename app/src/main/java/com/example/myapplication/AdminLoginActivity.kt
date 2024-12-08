package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // View'ları tanımla
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        // Varsayılan admin giriş bilgileri (örnek olarak)
        val adminEmail = "admin@gmail.com"
        val adminPassword = "admin123"

        // Giriş yap butonuna tıklama olayı
        loginButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()

            // Giriş bilgilerini doğrula
            if (validateInputs(enteredEmail, enteredPassword)) {
                if (checkCredentials(enteredEmail, enteredPassword, adminEmail, adminPassword)) {
                    // Giriş başarılı, AdminActivity'ye yönlendir
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    finish() // Bu aktiviteyi bitir
                } else {
                    // Hatalı giriş
                    Toast.makeText(this, "E-posta veya şifre hatalı!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Kullanıcı girişlerini kontrol et (Boş bırakılmasın)
    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                Toast.makeText(this, "Lütfen e-posta adresinizi girin!", Toast.LENGTH_SHORT).show()
                false
            }
            password.isEmpty() -> {
                Toast.makeText(this, "Lütfen şifrenizi girin!", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    // Giriş bilgilerini doğrula
    private fun checkCredentials(
        enteredEmail: String,
        enteredPassword: String,
        adminEmail: String,
        adminPassword: String
    ): Boolean {
        return enteredEmail == adminEmail && enteredPassword == adminPassword
    }
}