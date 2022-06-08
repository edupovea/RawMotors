package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import fragments.BuscarFragment
import fragments.InicioFragment
import fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_add_pieza.*
import kotlinx.android.synthetic.main.item_recycler_pieza.view.*
import models.Pieza
import recycler.PiezaAdapter

class InfladorActivity : AppCompatActivity() {


    private val inicioFragment = InicioFragment()
    private val buscarFragment = BuscarFragment()
    private val profileFragment = ProfileFragment()

    val menu_navigation: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.menu_navigation)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inflador)
        replaceFragment(inicioFragment)


        menu_navigation.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.ic_inicio -> replaceFragment(inicioFragment)
                R.id.ic_buscar -> replaceFragment(buscarFragment)
                R.id.ic_perfil -> replaceFragment(profileFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment!=null){
            val transaction= supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }

    }
}