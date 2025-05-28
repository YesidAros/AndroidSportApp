package com.example.sport_app.Activities

import android.content.Intent
import com.example.sport_app.Adapters.ProductAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_app.DataBase.Product
import com.example.sport_app.R
import android.widget.Button

class ResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var btnLocations: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)


        recyclerView = findViewById(R.id.recycler_product)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dummyProducts = listOf(
            Product(
                id = 1,
                name = "Andrés Cardona",
                description = "Entrenador con 5 años de experiencia",
                price = 50.0,
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
            ) ,
            Product(
                id = 3,
                name = "Javier Rodríguez",
                description = "Profesor de Boxeo",
                price = 40.0,
                imageResId = R.drawable.profile_picture,
                sportCategory = "Boxeo",
            )
        )

        productAdapter = ProductAdapter(dummyProducts)
        recyclerView.adapter = productAdapter


        btnLocations = findViewById(R.id.btn_locations)


        btnLocations.setOnClickListener {

            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}