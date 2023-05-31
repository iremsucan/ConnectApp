package com.iremsucan.connectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edt_email : EditText
    private lateinit var edt_password : EditText
    private lateinit var btn_signup : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var edtName : EditText
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edt_email = findViewById(R.id.email)
        edt_password = findViewById(R.id.password)
        btn_signup = findViewById(R.id.button_signup)
        edtName = findViewById(R.id.edt_name)

        btn_signup.setOnClickListener {
            val mail = edt_email.text.toString()
            val passwordd = edt_password.text.toString()
            val name = edtName.text.toString()

            signup (name, mail,passwordd)
        }
    }

    private fun signup (name: String, mail : String, passwordd : String ) {

        mAuth.createUserWithEmailAndPassword(mail, passwordd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, mail, mAuth.currentUser?.uid!!)
                    val intent =  Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Error occured" , Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, mail: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, mail, uid))

    }
}