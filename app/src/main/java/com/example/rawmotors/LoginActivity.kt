package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    val firestore by lazy { FirebaseFirestore.getInstance() }

    val btnLogin: Button by lazy {
        findViewById<Button>(R.id.btnLogin)
    }
    val btnRegistro: Button by lazy {
        findViewById<Button>(R.id.btnRegistro)
    }

    val btnForgotPass: Button by lazy {
        findViewById<Button>(R.id.btnForgotPass)
    }
    val user : EditText by lazy { findViewById(R.id.txtUser)}
    val pass : EditText by lazy { findViewById(R.id.txtPassword)}

    val auth = FirebaseAuth.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnRegistro.setOnClickListener {
            val intent: Intent =
                Intent(
                    this@LoginActivity,
                    RegistroActivity::class.java
                )
            startActivity(intent)
        }

        btnForgotPass.setOnClickListener {
            Toast.makeText(this@LoginActivity, getString(R.string.forgotPass) + " " +
                    user.text.toString(), Toast.LENGTH_SHORT).show()

            /*val tarea = auth.sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            R.string.emailRecuperacionEnviado,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            R.string.emailRecuperacionFallo,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }*/

        }



        btnLogin.setOnClickListener {
        //firestore.collection("users").where("author", "==", user.text.toString()).get()
            //val em=firestore.collection("users").whereEqualTo("user", user.text.toString())

            val tarea = auth.signInWithEmailAndPassword(
                user.text.toString(),
            pass.text.toString())
            tarea.addOnCompleteListener {
                if (it.isSuccessful){
                    val intent: Intent =
                        Intent(
                            this@LoginActivity,
                            InfladorActivity::class.java
                        )
                    startActivity(intent)
                    val user = auth.currentUser
                    Toast.makeText(this@LoginActivity, getString(R.string.loginOk) + " " +
                            user?.email.toString(), Toast.LENGTH_SHORT).show()
                }else {
                    try {
                        throw tarea.exception!!;
                    } catch (ex: FirebaseAuthInvalidUserException) {
                        Toast.makeText(
                            this@LoginActivity,
                            R.string.usuarioNoExiste,
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (ex: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            this@LoginActivity,
                            R.string.contraseñaInvalida,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }




    }
}