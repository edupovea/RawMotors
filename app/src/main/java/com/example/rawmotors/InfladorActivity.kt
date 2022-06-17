package com.example.rawmotors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragments.BuscarFragment
import fragments.InicioFragment
import fragments.ProfileFragment

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

        //Menu navegacion inferior trabajado con fragments
        menu_navigation.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.ic_inicio -> replaceFragment(inicioFragment)
                R.id.ic_buscar -> replaceFragment(buscarFragment)
                R.id.ic_perfil -> replaceFragment(profileFragment)
            }
            true
        }
    }




    //Funcion que hace efectivo el uso del menu de navegacion y cambio de fragments de manera dinamica
    private fun replaceFragment(fragment: Fragment){
        if (fragment!=null){
            val transaction= supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }

    }
}