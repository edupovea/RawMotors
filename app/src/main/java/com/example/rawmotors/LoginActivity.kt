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

class LoginActivity : AppCompatActivity() {

    val btnLogin: Button by lazy {
        findViewById<Button>(R.id.btnLogin)
    }
    val btnRegistro: Button by lazy {
        findViewById<Button>(R.id.btnRegistro)
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


        btnLogin.setOnClickListener {
        val tarea = auth.signInWithEmailAndPassword(
            user.text.toString(),
            pass.text.toString())
            tarea.addOnCompleteListener {
                if (it.isSuccessful){
                    val user = auth.currentUser
                    Toast.makeText(this@LoginActivity, "Usuario logeado correctamente"+ " " +
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