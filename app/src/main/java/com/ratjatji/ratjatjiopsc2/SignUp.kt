package com.ratjatji.ratjatjiopsc2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var etName : EditText
    private lateinit var etEmail :EditText
    private lateinit var etCompanyName :EditText
    private lateinit var etPassword :EditText
    private lateinit var btnRegister : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etCompanyName = findViewById(R.id.etCompanyName)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)


        btnRegister.setOnClickListener{
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            signUp(name, email,password)
        }
        }
    private fun signUp(name: String, email:String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Code to add new user to database
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    //User has created an account and is being taken to the user dashboard
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    //User is unable to sign up
                    Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
                }
    }
    }
private fun addUserToDatabase(name:String, email:String, uid:String){
mDbRef = FirebaseDatabase.getInstance().getReference()

    mDbRef.child("user").child(uid).setValue(User(name, email, uid))
}
}
