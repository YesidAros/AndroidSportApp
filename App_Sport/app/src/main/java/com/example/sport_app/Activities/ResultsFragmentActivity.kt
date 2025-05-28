package com.example.sport_app.Activities

import ProductAdapterActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sport_app.R
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_app.Fragments.Product

class ResultsFragmentActivity : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapterActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.recycler_product)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val dummyProducts = listOf(
            Product(
                id = 1,
                name = "Andrés Cardona",
                description = "Entrenador con 5 años de experiencia",
                price = 50.0, // Como Double
                imageResId = R.drawable.profile_picture,
                sportCategory = "Fútbol",
            ),
            Product(
                id = 2,
                name = "Laura Gómez",
                description = "Certificada en yoga terapéutico",
                price = 40.0,
                imageResId = R.drawable.profile_picture,
                sportCategory = "Yoga",
            )

        )

        productAdapter = ProductAdapterActivity(dummyProducts)
        recyclerView.adapter = productAdapter
    }
}