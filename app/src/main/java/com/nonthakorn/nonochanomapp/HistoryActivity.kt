package com.nonthakorn.nonochanomapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

// Updated Order Data Model with multilingual support
data class Order(
    val id: String,
    val dateTime: String,
    val statusEn: String,
    val statusTh: String,
    val statusColor: Int,
    val menuEn: String,
    val menuTh: String,
    val menuSizeEn: String,
    val menuSizeTh: String,
    val toppingsEn: String,
    val toppingsTh: String,
    val imageResource: Int,
    val price: Double = 0.0
) {
    // Helper functions to get localized content
    fun getStatus(isEnglish: Boolean): String {
        return if (isEnglish) statusEn else statusTh
    }

    fun getMenu(isEnglish: Boolean): String {
        return if (isEnglish) menuEn else menuTh
    }

    fun getMenuSize(isEnglish: Boolean): String {
        return if (isEnglish) menuSizeEn else menuSizeTh
    }

    fun getToppings(isEnglish: Boolean): String {
        return if (isEnglish) toppingsEn else toppingsTh
    }
}

// Updated ViewHolder Class
class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
    private val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
    private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    private val tvMenu: TextView = itemView.findViewById(R.id.tvMenu)
    private val tvMenuSize: TextView = itemView.findViewById(R.id.tvMenuSize)
    private val tvToppings: TextView = itemView.findViewById(R.id.tvToppings)

    fun bind(order: Order, isEnglish: Boolean) {
        imageProduct.setImageResource(order.imageResource)
        tvDateTime.text = order.dateTime
        tvStatus.text = order.getStatus(isEnglish)
        tvStatus.setTextColor(order.statusColor)

        // Set localized text with proper labels
        val context = itemView.context
        tvMenu.text = if (isEnglish) {
            "Menu: ${order.getMenu(isEnglish)}"
        } else {
            "เมนู: ${order.getMenu(isEnglish)}"
        }

        tvMenuSize.text = if (isEnglish) {
            "Cup Size: ${order.getMenuSize(isEnglish)}"
        } else {
            "ขนาดแก้ว: ${order.getMenuSize(isEnglish)}"
        }

        tvToppings.text = order.getToppings(isEnglish)
    }
}

// Updated Adapter Class
class OrderAdapter(private var orders: List<Order>) : RecyclerView.Adapter<OrderViewHolder>() {

    private var isEnglish = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        isEnglish = LocaleHelper.isEnglishLanguage(parent.context)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position], isEnglish)
    }

    override fun getItemCount(): Int = orders.size

    // Function to update data
    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }

    // Function to update language
    fun updateLanguage(context: android.content.Context) {
        isEnglish = LocaleHelper.isEnglishLanguage(context)
        notifyDataSetChanged()
    }

    // Function to add single order
    fun addOrder(order: Order) {
        val mutableOrders = orders.toMutableList()
        mutableOrders.add(0, order) // Add to top
        orders = mutableOrders
        notifyItemInserted(0)
    }
}

// Updated Main History Activity
class HistoryActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        val nav_home: ImageView = findViewById(R.id.nav_home)
        val nav_user: ImageView = findViewById(R.id.nav_user)
        val icon_flag: ImageView = findViewById(R.id.icon_flag)

        // อัพเดท flag icon ตามภาษาปัจจุบัน
        updateFlagIcon()

        nav_home.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        nav_user.setOnClickListener {
            val intent = Intent(this, UserinfoActivity::class.java)
            startActivity(intent)
        }

        icon_flag.setOnClickListener {
            val intent = Intent(this, LanguageswtichActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        loadOrderData()
    }

    private fun updateFlagIcon() {
        val icon_flag: ImageView = findViewById(R.id.icon_flag)
        if (LocaleHelper.isEnglishLanguage(this)) {
            icon_flag.setImageResource(R.drawable.ic_thailand) // Show Thai flag when in English
        } else {
            icon_flag.setImageResource(R.drawable.ic_thailand) // Show Thai flag (you can change to UK flag)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewOrders)
        orderAdapter = OrderAdapter(emptyList())

        recyclerView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            val dividerItemDecoration = DividerItemDecoration(this@HistoryActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun loadOrderData() {
        // Sample order data with multilingual support
        val sampleOrders = listOf(
            Order(
                id = "001",
                dateTime = if (LocaleHelper.isEnglishLanguage(this)) "Mar 15, 2024 2:30 PM" else "15 มี.ค. 2567 14:30",
                statusEn = "Completed",
                statusTh = "เสร็จสิ้น",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_green_dark),
                menuEn = "Taro",
                menuTh = "เผือก",
                menuSizeEn = "Small",
                menuSizeTh = "เล็ก",
                toppingsEn = "Toppings: Bubble",
                toppingsTh = "ท็อปปิ้ง: ไข่มุก",
                imageResource = R.drawable.product10,
                price = 10.0
            ),
            Order(
                id = "002",
                dateTime = if (LocaleHelper.isEnglishLanguage(this)) "Mar 15, 2024 1:45 PM" else "15 มี.ค. 2567 13:45",
                statusEn = "Preparing",
                statusTh = "กำลังทำ",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_orange_dark),
                menuEn = "Yogurt",
                menuTh = "โยเกิร์ต",
                menuSizeEn = "Medium",
                menuSizeTh = "กลาง",
                toppingsEn = "Toppings: Jelly",
                toppingsTh = "ท็อปปิ้ง: เยลลี่",
                imageResource = R.drawable.product02,
                price = 20.0
            ),
            Order(
                id = "003",
                dateTime = if (LocaleHelper.isEnglishLanguage(this)) "Mar 15, 2024 12:20 PM" else "15 มี.ค. 2567 12:20",
                statusEn = "Cancelled",
                statusTh = "ยกเลิก",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_red_dark),
                menuEn = "Fresh Milk",
                menuTh = "นมสด",
                menuSizeEn = "Large",
                menuSizeTh = "ใหญ่",
                toppingsEn = "Toppings: None",
                toppingsTh = "ท็อปปิ้ง: ไม่มี",
                imageResource = R.drawable.product06,
                price = 35.0
            ),
            Order(
                id = "004",
                dateTime = if (LocaleHelper.isEnglishLanguage(this)) "Mar 14, 2024 4:15 PM" else "14 มี.ค. 2567 16:15",
                statusEn = "Completed",
                statusTh = "เสร็จสิ้น",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_green_dark),
                menuEn = "Strawberry",
                menuTh = "สตอเบอร์รี่",
                menuSizeEn = "Small",
                menuSizeTh = "เล็ก",
                toppingsEn = "Toppings: Jelly",
                toppingsTh = "ท็อปปิ้ง: เยลลี่",
                imageResource = R.drawable.product04,
                price = 10.0
            ),
            Order(
                id = "005",
                dateTime = if (LocaleHelper.isEnglishLanguage(this)) "Mar 14, 2024 11:30 AM" else "14 มี.ค. 2567 11:30",
                statusEn = "Cancelled",
                statusTh = "ยกเลิก",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_red_dark),
                menuEn = "Cocoa",
                menuTh = "โกโก้",
                menuSizeEn = "Medium",
                menuSizeTh = "กลาง",
                toppingsEn = "Toppings: Bubble",
                toppingsTh = "ท็อปปิ้ง: ไข่มุก",
                imageResource = R.drawable.product05,
                price = 20.0
            ),
            Order(
                id = "006",
                dateTime = if (LocaleHelper.isEnglishLanguage(this)) "Mar 13, 2024 9:00 AM" else "13 มี.ค. 2567 09:00",
                statusEn = "Completed",
                statusTh = "เสร็จสิ้น",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_green_dark),
                menuEn = "Thai Tea",
                menuTh = "ชาไทย",
                menuSizeEn = "Large",
                menuSizeTh = "ใหญ่",
                toppingsEn = "Toppings: None",
                toppingsTh = "ท็อปปิ้ง: ไม่มี",
                imageResource = R.drawable.product08,
                price = 35.0
            )
        )

        orderAdapter.updateOrders(sampleOrders)
    }

    // Function to simulate adding new order (สำหรับทดสอบ)
    private fun addNewOrder() {
        val isEnglish = LocaleHelper.isEnglishLanguage(this)
        val dateFormat = if (isEnglish) {
            SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.ENGLISH)
        } else {
            SimpleDateFormat("dd MMM yyyy HH:mm", Locale("th", "TH"))
        }

        val newOrder = Order(
            id = System.currentTimeMillis().toString(),
            dateTime = dateFormat.format(Date()),
            statusEn = "Pending Confirmation",
            statusTh = "รอการยืนยัน",
            statusColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark),
            menuEn = "New Menu",
            menuTh = "เมนูใหม่",
            menuSizeEn = "Medium",
            menuSizeTh = "กลาง",
            toppingsEn = "Toppings: Custom",
            toppingsTh = "ท็อปปิ้ง: ตามสั่ง",
            imageResource = R.drawable.logo_nono,
            price = 50.0
        )

        orderAdapter.addOrder(newOrder)
        recyclerView.smoothScrollToPosition(0) // Scroll to top to show new item
    }

    override fun onResume() {
        super.onResume()
        // อัพเดทข้อมูลเมื่อกลับมาหน้านี้
        orderAdapter.updateLanguage(this)
        updateFlagIcon()
        loadOrderData() // Reload data to update language
    }
}