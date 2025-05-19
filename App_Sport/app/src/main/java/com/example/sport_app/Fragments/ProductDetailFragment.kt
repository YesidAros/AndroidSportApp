package com.example.sport_app.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatViewInflater
import androidx.fragment.app.Fragment
import com.example.sport_app.R
import com.example.sport_app.Fragments.Product

class ProductDetailFragment : Fragment() {

    private lateinit var product: Product

    companion object {
        private const val ARG_PRODUCT = "product"
        fun newInstance(product: Product): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Verify if the fragment arguments are not null
        if (arguments != null){
            //starting Android 13 (Api33) getParcelable() should be used.
            product = if (android.os.Build.VERSION.SDK_INT >= 33) {
                arguments?.getParcelable(ARG_PRODUCT, Product::class.java)
                    ?: Product(0, "Not Name", "Not Description", 0.0, R.drawable.profile_picture) // <--- CORREGIDO
            } else { // <--- CORREGIDO: "else" en la misma línea o con indentación correcta
                @Suppress("DEPRECATION")
                arguments?.getParcelable<Product>(ARG_PRODUCT) // <--- CORREGIDO: Añadir tipo genérico
                    ?: Product(0, "Not Name", "Not Description", 0.0, R.drawable.profile_picture) // <--- CORREGIDO
            }
        } else {
            //If there no arguments, set an empty product
            product = Product(0, "Not Name", "Not Description", 0.0, R.drawable.profile_picture) // <--- CORREGIDO
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)

        val productImageView = view.findViewById<ImageView>(R.id.iv_picture_service)
        val productNameTextView = view.findViewById<TextView>(R.id.tv_service_name)
        val priceTextView = view.findViewById<TextView>(R.id.tv_price)
        val descriptionTextView = view.findViewById<TextView>(R.id.tv_info)
        val messageIcon = view.findViewById<ImageView>(R.id.iv_message)
        val messageTextView = view.findViewById<TextView>(R.id.tv_message)
        val contactIcon = view.findViewById<ImageView>(R.id.iv_call)
        val contactTextView = view.findViewById<TextView>(R.id.tv_call)

        productImageView.setImageResource(product.imageResId)
        productNameTextView.text = product.name
        priceTextView.text = "Price: $${product.price}"
        descriptionTextView.text = product.description

        messageIcon.setOnClickListener {
                    }

        contactIcon.setOnClickListener {

        }

        return view



    }

}