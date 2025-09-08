package com.nonthakorn.nonochanomapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Product Data Model
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageResource: Int,
    val category: String = "drink",
    val isAvailable: Boolean = true
)

// Product ViewHolder
class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
    private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
    private val tvProductPrice: TextView = itemView.findViewById(R.id.tvPrice)

    fun bind(product: Product, onItemClick: (Product) -> Unit) {
        imageProduct.setImageResource(product.imageResource)
        tvProductName.text = product.name
        tvProductPrice.text = "${product.price.toInt()} บาท"

        // Set click listener
        itemView.setOnClickListener {
            onItemClick(product)
        }

        // Set alpha based on availability
        itemView.alpha = if (product.isAvailable) 1.0f else 0.6f
    }
}

// Product Adapter
class ProductAdapter(
    private var products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], onItemClick)
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    fun filterProducts(query: String) {
        // สำหรับการค้นหาในอนาคต
    }
}

// Main MenuActivity
class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerViewProducts: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    private lateinit var navHome: ImageView
    private lateinit var navHistory: ImageView
    private lateinit var navUser: ImageView
    private lateinit var iconFlag: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        loadProductData()
        setupNavigationListeners()
    }

    private fun initViews() {
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts)
        navHome = findViewById(R.id.nav_home)
        navHistory = findViewById(R.id.nav_history)
        navUser = findViewById(R.id.nav_user)
        iconFlag = findViewById(R.id.icon_flag)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList()) { product ->
            onProductClick(product)
        }

        recyclerViewProducts.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@MenuActivity, 2)
        }
    }

    private fun loadProductData() {
        // ข้อมูลสินค้าตัวอย่าง
        val sampleProducts = listOf(
            Product(
                id = "1",
                name = "มะพร้าว",
                price = 45.0,
                description = "มะพร้าว",
                imageResource = R.drawable.product01
            ),
            Product(
                id = "2",
                name = "โยเกิร์ต",
                price = 55.0,
                description = "โยเกิร์ต",
                imageResource = R.drawable.product02
            ),
            Product(
                id = "3",
                name = "โคล่า",
                price = 40.0,
                description = "โคล่า",
                imageResource = R.drawable.product03
            ),
            Product(
                id = "4",
                name = "สตอเบอร์รี่",
                price = 50.0,
                description = "สตอเบอร์รี่",
                imageResource = R.drawable.product04
            ),
            Product(
                id = "5",
                name = "โกโก้",
                price = 60.0,
                description = "โกโก้",
                imageResource = R.drawable.product05
            ),
            Product(
                id = "6",
                name = "นมสด",
                price = 35.0,
                description = "นมสด",
                imageResource = R.drawable.product06
            ),
            Product(
                id = "7",
                name = "ชาเขียว",
                price = 65.0,
                description = "ชาเขียว",
                imageResource = R.drawable.product07
            ),
            Product(
                id = "8",
                name = "ชาไทย",
                price = 35.0,
                description = "ชาไทย",
                imageResource = R.drawable.product08
            ),
            Product(
                id = "9",
                name = "แคนตาลูป",
                price = 70.0,
                description = "เเคนตาลูป",
                imageResource = R.drawable.product09,
                isAvailable = false
            ),
            Product(
                id = "10",
                name = "เผือก",
                price = 45.0,
                description = "เผือก",
                imageResource = R.drawable.product10
            )
        )

        productAdapter.updateProducts(sampleProducts)
    }


    private fun onProductClick(product: Product) {

        val intent = Intent(this, MenudetailActivity::class.java)

        if (!product.isAvailable) {
            Toast.makeText(this, "${product.name} ขณะนี้ไม่พร้อมให้บริการ", Toast.LENGTH_SHORT).show()
            return
        }

        // Navigate to product detail or order activity
        Toast.makeText(this, "เลือก ${product.name} - ${product.price.toInt()} บาท", Toast.LENGTH_SHORT).show()

        // ตัวอย่าง: ส่งไปยังหน้า Order Detail
        // val intent = Intent(this, OrderDetailActivity::class.java)
        // intent.putExtra("product_id", product.id)
        // intent.putExtra("product_name", product.name)
        // intent.putExtra("product_price", product.price)
        // startActivity(intent)
    }

    private fun setupNavigationListeners() {
        navHome.setOnClickListener {
            // Navigate to Home
            Toast.makeText(this, "หน้าหลัก", Toast.LENGTH_SHORT).show()
        }

        navHistory.setOnClickListener {
            // Navigate to History
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        navUser.setOnClickListener {
            // Navigate to User Profile
            Toast.makeText(this, "โปรไฟล์", Toast.LENGTH_SHORT).show()
             val intent = Intent(this, UserinfoActivity::class.java)
             startActivity(intent)
        }

        iconFlag.setOnClickListener {
            // Language switch functionality
            Toast.makeText(this, "เปลี่ยนภาษา", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LanguageswtichActivity::class.java)
            startActivity(intent)
        }
    }
}
