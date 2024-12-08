package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CustomerLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()

            if (validateInputs(enteredEmail, enteredPassword)) {
                manualLogin(enteredEmail, enteredPassword)
            }
        }
    }

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

    private fun manualLogin(email: String, password: String) {
        // Statik kontrol: Belirli bir e-posta ve şifreyi kontrol et
        if (email == "irem@gmail.com" && password == "irem123") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "E-posta veya şifre hatalı!", Toast.LENGTH_SHORT).show()
        }
    }
}