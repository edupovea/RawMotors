package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    val firestore by lazy { FirebaseFirestore.getInstance() }

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

            //Guardamos los valores de user y email en una coleccion para mostrar el user en la pantalla profile
                val valoresLogin: HashMap<String, Any> =
                    HashMap<String, Any>()
                valoresLogin.put("Usuario" , user.text.toString())
                valoresLogin.put("Email" , email.text.toString())

                val tareaInsertar= firestore.collection("users").document(email.text.toString()).set(valoresLogin)

                
                tarea.addOnCompleteListener ( this ,
                    object : OnCompleteListener<AuthResult>{
                        override fun onComplete(p0: Task<AuthResult>) {
                            if (tarea.isSuccessful){
                                val intent: Intent =
                                    Intent(
                                        this@RegistroActivity,
                                        LoginActivity::class.java
                                    )
                                startActivity(intent)
                            }else{
                                //Capturamos las excepciones posibles de Firebase
                                try {
                                    throw tarea.exception!!
                                } catch (e: FirebaseAuthWeakPasswordException) {
                                    showAlert(R.string.contraseñaDebil)

                                } catch (e: FirebaseAuthInvalidCredentialsException) {
                                    showAlert(R.string.credencialesInvalidos)

                                } catch (e: FirebaseAuthUserCollisionException) {
                                    showAlert(R.string.colisionUsuario)
                                } catch (e: Exception) {
                                    showAlert(R.string.errorDesconocido)

                                }
                            }
                        }


                    })
            }else{
                showAlert(R.string.camposRegistroNoRellenos)

            }
            }
        }

    private fun showAlert(msj: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.loginFallido)
        builder.setMessage(msj)
        builder.setPositiveButton(R.string.reintentar, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    }
