package com.artemiod.challengeandroidkotlinalkemy.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import com.artemiod.challengeandroidkotlinalkemy.R
import com.google.firebase.FirebaseApp

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        if (currentUser != null) {

            goToSplashActivity()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isValidCredentials(email, password)) {
                signIn(email, password)
            }
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {

        val validEmail = "ejemploe@idat.com.pe"
        val validPassword = "Peru123"

        return if (email == validEmail && password == validPassword) {
            true
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    goToSplashActivity()
                } else {

                    Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun goToSplashActivity() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
}
