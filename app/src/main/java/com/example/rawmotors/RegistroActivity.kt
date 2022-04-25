package com.example.rawmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

class RegistroActivity : AppCompatActivity() {

    val email: EditText by lazy { findViewById(R.id.txtEmailReg) }
    val user : EditText by lazy { findViewById(R.id.txtUserReg)}
    val pass : EditText by lazy { findViewById(R.id.txtPassReg)}

    val auth by lazy { FirebaseAuth.getInstance() }

    val btnConfirm: Button by lazy {
        findViewById<Button>(R.id.btnConfirm)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnConfirm.setOnClickListener {
            if (!email.text.isBlank() && !pass.text.isBlank() && !user.text.isBlank()) {
                val tarea = auth.createUserWithEmailAndPassword(
                    email.text.toString(), pass.text.toString()
                )

                tarea.addOnCompleteListener ( this ,
                    object : OnCompleteListener<AuthResult>{
                        override fun onComplete(p0: Task<AuthResult>) {
                            if (tarea.isSuccessful){
                                Toast.makeText(
                                    this@RegistroActivity,
                                    getString(R.string.registroCompletado)
                                            + " " + auth.currentUser?.email.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else{
                                try {
                                    throw tarea.exception!!
                                } catch (e: FirebaseAuthWeakPasswordException) {
                                    Toast.makeText(
                                        this@RegistroActivity,
                                        R.string.contraseñaDebil,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } catch (e: FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(
                                        this@RegistroActivity,
                                        R.string.credencialesInvalidos,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } catch (e: FirebaseAuthUserCollisionException) {
                                    Toast.makeText(
                                        this@RegistroActivity,
                                        R.string.colisionUsuario,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        this@RegistroActivity,
                                        R.string.errorDesconocido,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }


                    })                    }else{
                Toast.makeText(
                    this@RegistroActivity,R.string.camposRegistroNoRellenos,
                    Toast.LENGTH_LONG
                ).show()

            }
            }
        }


    }
