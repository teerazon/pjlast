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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Product Data Model with multilingual support
data class Product(
    val id: String,
    val nameEn: String,
    val nameTh: String,
    val price: Double,
    val descriptionEn: String,
    val descriptionTh: String,
    val imageResource: Int,
    val category: String = "drink",
    val isAvailable: Boolean = true
) {
    // Helper functions to get localized content
    fun getName(isEnglish: Boolean): String {
        return if (isEnglish) nameEn else nameTh
    }

    fun getDescription(isEnglish: Boolean): String {
        return if (isEnglish) descriptionEn else descriptionTh
    }
}

// Product ViewHolder
class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
    private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
    private val tvProductPrice: TextView = itemView.findViewById(R.id.tvPrice)

    fun bind(product: Product, onItemClick: (Product) -> Unit, isEnglish: Boolean) {
        imageProduct.setImageResource(product.imageResource)
        tvProductName.text = product.getName(isEnglish)
        tvProductPrice.text = if (isEnglish) {
            "${product.price.toInt()} Baht"
        } else {
            "${product.price.toInt()} บาท"
        }

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

    private var isEnglish = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        isEnglish = LocaleHelper.isEnglishLanguage(parent.context)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], onItemClick, isEnglish)
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    fun updateLanguage(context: android.content.Context) {
        isEnglish = LocaleHelper.isEnglishLanguage(context)
        notifyDataSetChanged()
    }
}

// Main MenuActivity
class MenuActivity : BaseActivity() {

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

        // อัพเดท flag icon ตามภาษาปัจจุบัน
        updateFlagIcon()
    }

    private fun updateFlagIcon() {
        if (LocaleHelper.isEnglishLanguage(this)) {
            iconFlag.setImageResource(R.drawable.ic_uk) // ต้องเพิ่ม icon ธงอังกฤษ
        } else {
            iconFlag.setImageResource(R.drawable.ic_thailand)
        }
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
        // ข้อมูลสินค้าตัวอย่างแบบหลายภาษา
        val sampleProducts = listOf(
            Product(
                id = "1",
                nameEn = "Coconut",
                nameTh = "มะพร้าว",
                price = 20.0,
                descriptionEn = "Fresh coconut water",
                descriptionTh = "น้ำมะพร้าวสด",
                imageResource = R.drawable.product01
            ),
            Product(
                id = "2",
                nameEn = "Yogurt",
                nameTh = "โยเกิร์ต",
                price = 20.0,
                descriptionEn = "Creamy yogurt drink",
                descriptionTh = "โยเกิร์ตเครื่องดื่ม",
                imageResource = R.drawable.product02
            ),
            Product(
                id = "3",
                nameEn = "Cola",
                nameTh = "โคล่า",
                price = 20.0,
                descriptionEn = "Cola drink",
                descriptionTh = "เครื่องดื่มโคล่า",
                imageResource = R.drawable.product03
            ),
            Product(
                id = "4",
                nameEn = "Strawberry",
                nameTh = "สตอเบอร์รี่",
                price = 20.0,
                descriptionEn = "Fresh strawberry drink",
                descriptionTh = "เครื่องดื่มสตอเบอร์รี่สด",
                imageResource = R.drawable.product04
            ),
            Product(
                id = "5",
                nameEn = "Cocoa",
                nameTh = "โกโก้",
                price = 20.0,
                descriptionEn = "Hot cocoa drink",
                descriptionTh = "เครื่องดื่มโกโก้ร้อน",
                imageResource = R.drawable.product05
            ),
            Product(
                id = "6",
                nameEn = "Fresh Milk",
                nameTh = "นมสด",
                price = 20.0,
                descriptionEn = "Pure fresh milk",
                descriptionTh = "นมสดบริสุทธิ์",
                imageResource = R.drawable.product06
            ),
            Product(
                id = "7",
                nameEn = "Green Tea",
                nameTh = "ชาเขียว",
                price = 20.0,
                descriptionEn = "Premium green tea",
                descriptionTh = "ชาเขียวพรีเมี่ยม",
                imageResource = R.drawable.product07
            ),
            Product(
                id = "8",
                nameEn = "Thai Tea",
                nameTh = "ชาไทย",
                price = 20.0,
                descriptionEn = "Traditional Thai tea",
                descriptionTh = "ชาไทยแท้",
                imageResource = R.drawable.product08
            ),
            Product(
                id = "9",
                nameEn = "Cantaloupe",
                nameTh = "แคนตาลูป",
                price = 20.0,
                descriptionEn = "Sweet cantaloupe juice",
                descriptionTh = "น้ำแคนตาลูปหวาน",
                imageResource = R.drawable.product09,
                isAvailable = false
            ),
            Product(
                id = "10",
                nameEn = "Taro",
                nameTh = "เผือก",
                price = 20.0,
                descriptionEn = "Taro milk tea",
                descriptionTh = "ชานมเผือก",
                imageResource = R.drawable.product10
            )
        )

        productAdapter.updateProducts(sampleProducts)
    }

    private fun onProductClick(product: Product) {
        if (!product.isAvailable) {
            val message = if (LocaleHelper.isEnglishLanguage(this)) {
                "${product.getName(true)} is currently not available"
            } else {
                "${product.getName(false)} ขณะนี้ไม่พร้อมให้บริการ"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            return
        }

        // Navigate to product detail
        val intent = Intent(this, MenudetailActivity::class.java)
        intent.putExtra("product_id", product.id)
        intent.putExtra("product_name_en", product.nameEn)
        intent.putExtra("product_name_th", product.nameTh)
        intent.putExtra("product_price", product.price)
        startActivity(intent)

        val message = if (LocaleHelper.isEnglishLanguage(this)) {
            "Selected ${product.getName(true)} - ${product.price.toInt()} Baht"
        } else {
            "เลือก ${product.getName(false)} - ${product.price.toInt()} บาท"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupNavigationListeners() {
        navHome.setOnClickListener {
            val message = if (LocaleHelper.isEnglishLanguage(this)) "Home" else "หน้าหลัก"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        navHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        navUser.setOnClickListener {
            val intent = Intent(this, UserinfoActivity::class.java)
            startActivity(intent)
        }

        iconFlag.setOnClickListener {
            val intent = Intent(this, LanguageswtichActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // อัพเดทข้อมูลเมื่อกลับมาหน้านี้
        productAdapter.updateLanguage(this)
        updateFlagIcon()
    }
}