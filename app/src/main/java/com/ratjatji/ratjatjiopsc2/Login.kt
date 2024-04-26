package com.ratjatji.ratjatjiopsc2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSubmit: Button
    private lateinit var tvRegister: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
            etEmail = findViewById(R.id.etEmail)
            etPassword = findViewById(R.id.etPassword)
            tvRegister = findViewById(R.id.tvRegister)
            btnSubmit = findViewById(R.id.btnSubmit)

        tvRegister.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            finish()
            startActivity(intent)
        }

        btnSubmit.setOnClickListener{
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            login(email,password)
        }
    }
    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //User does have an existing account and is taken to the user dashboard
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    //User does NOT have an existing account
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_LONG).show()
                }
            }
    }
}