package com.iremsucan.connectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edt_email : EditText
    private lateinit var edt_password : EditText
    private lateinit var btn_login : Button
    private lateinit var btn_signup : Button
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edt_email = findViewById(R.id.email)
        edt_password = findViewById(R.id.password)
        btn_login = findViewById(R.id.button_login)
        btn_signup = findViewById(R.id.button_signup)
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUp ::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val mail = edt_email.text.toString()
            val passwordd = edt_password.text.toString()

            login(mail,passwordd)
        }
    }

    private fun login (mail : String, passwordd : String ) {

        mAuth.signInWithEmailAndPassword(mail, passwordd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =  Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                  Toast.makeText(this@Login, "User doesn't exist!", Toast.LENGTH_SHORT).show()
                }
            }

    }
}