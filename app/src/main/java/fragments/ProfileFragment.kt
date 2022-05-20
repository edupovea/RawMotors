package fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.rawmotors.AddPiezaActivity
import com.example.rawmotors.R
import kotlinx.android.synthetic.main.activity_profile_fragment.*
import models.Pieza

class ProfileFragment : Fragment() {

    private lateinit var piezaArrayList: ArrayList<Pieza>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddPieza.setOnClickListener {
            val cambiarPantalla = Intent(context, AddPiezaActivity::class.java)
            startActivity(cambiarPantalla)
        }


    }
}