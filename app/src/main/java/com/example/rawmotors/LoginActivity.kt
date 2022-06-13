package com.example.rawmotors

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val firestore by lazy { FirebaseFirestore.getInstance() }


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
            val tarea = auth.sendPasswordResetEmail(user.text.toString())

                tarea.addOnCompleteListener {
                    if (it.isSuccessful) {
                        showAlert(R.string.emailRecuperacionEnviado, R.string.checkEmail, R.string.continuar)

                    } else {
                        showAlert(R.string.emailRecuperacionFallo, R.string.error, R.string.reintentar)
                    }
                }

        }



        btnLogin.setOnClickListener {

            val tarea = auth.signInWithEmailAndPassword(
                user.text.toString(),
            pass.text.toString())
            tarea.addOnCompleteListener {
                if (it.isSuccessful){
                    savePrefs()
                    val intent: Intent =
                        Intent(
                            this@LoginActivity,
                            InfladorActivity::class.java
                        )
                    startActivity(intent)

                }else {
                    try {
                        throw tarea.exception!!;
                    } catch (ex: FirebaseAuthInvalidUserException) {
                        showAlert(R.string.usuarioNoExiste,R.string.error,R.string.reintentar)

                    } catch (ex: FirebaseAuthInvalidCredentialsException) {
                        showAlert(R.string.contraseñaInvalida,R.string.error,R.string.reintentar)
                    }
                }
            }

        }




    }
    private fun showAlert(msj: Int, title : Int, btn : Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msj)
        builder.setPositiveButton(btn, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun savePrefs(){
        //Este sirve para leer preferencias
        var preferencias: SharedPreferences =
            //SIEMPRE LO PONEMOS MODO PRIVADO
            this.getPreferences(MODE_PRIVATE)

        //Este sirve para escribir o editar preferencias
        var prefEditor: SharedPreferences.Editor =
            preferencias.edit()

        prefEditor.putString("usuarioPorDefecto", user.text.toString())
        prefEditor.putString("contraseñaPorDefecto",pass.text.toString())

        prefEditor.commit()
    }

    override fun onStart() {
        super.onStart()
        val prefs:SharedPreferences=this.getPreferences(MODE_PRIVATE)

        user.setText(prefs.getString("usuarioPorDefecto",""))
        pass.setText(prefs.getString("contraseñaPorDefecto",""))

    }
}