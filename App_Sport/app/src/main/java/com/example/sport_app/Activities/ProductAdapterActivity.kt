import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_app.R
import com.example.sport_app.Fragments.Product


class ProductAdapterActivity(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapterActivity.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfile = itemView.findViewById<ImageView>(R.id.iv_service_Profile_picture)
        val tvPrice = itemView.findViewById<TextView>(R.id.tv_price)
        val tvName = itemView.findViewById<TextView>(R.id.profile_name1)
        val tvCategory = itemView.findViewById<TextView>(R.id.tv_sport_category)
        val tvInfo = itemView.findViewById<TextView>(R.id.profile_info1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.ivProfile.setImageResource(product.imageResId)
        holder.tvPrice.text = product.price.toString()
        holder.tvName.text = product.name
        holder.tvCategory.text = product.sportCategory
    }

    override fun getItemCount(): Int = productList.size
}
