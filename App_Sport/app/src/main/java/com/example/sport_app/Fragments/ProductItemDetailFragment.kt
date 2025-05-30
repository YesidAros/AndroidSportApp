package com.example.sport_app.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sport_app.R
import com.example.sport_app.DataBase.Product

class ProductItemDetailFragment : Fragment() {

    private lateinit var product: Product

    companion object {
        private const val ARG_PRODUCT = "product"
        fun newInstance(product: Product): ProductItemDetailFragment {
            val fragment = ProductItemDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            product = if (android.os.Build.VERSION.SDK_INT >= 33) {
                arguments?.getParcelable(ARG_PRODUCT, Product::class.java)
                    ?: Product(0, "Not Name", "Not Description", 0.0, R.drawable.profile_picture, "Default Category") // <--- ADD DEFAULT VALUE
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable<Product>(ARG_PRODUCT)
                    ?: Product(0, "Not Name", "Not Description", 0.0, R.drawable.profile_picture, "Default Category") // <--- ADD DEFAULT VALUE
            }
        } else {
            product = Product(0, "Not Name", "Not Description", 0.0, R.drawable.profile_picture, "Default Category") // <--- ADD DEFAULT VALUE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_item_detail, container, false)

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