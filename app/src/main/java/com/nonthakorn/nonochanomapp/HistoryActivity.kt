package com.nonthakorn.nonochanomapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

// Data Model Class
data class Order(
    val id: String,
    val dateTime: String,
    val status: String,
    val statusColor: Int,
    val menu: String,
    val menuSize: String,
    val toppings: String,
    val imageResource: Int
)

// ViewHolder Class
class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
    private val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
    private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    private val tvMenu: TextView = itemView.findViewById(R.id.tvMenu)
    private val tvMenuSize: TextView = itemView.findViewById(R.id.tvMenuSize)
    private val tvToppings: TextView = itemView.findViewById(R.id.tvToppings)

    fun bind(order: Order) {
        imageProduct.setImageResource(order.imageResource)
        tvDateTime.text = order.dateTime
        tvStatus.text = order.status
        tvStatus.setTextColor(order.statusColor)
        tvMenu.text = "เมนู : ${order.menu}"
        tvMenuSize.text = "ขนาดแก้ว : ${order.menuSize}"
        tvToppings.text = order.toppings
    }
}

// Adapter Class
class OrderAdapter(private var orders: List<Order>) : RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    // Function to update data
    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
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

// Main Activity
class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        loadOrderData()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewOrders)
        orderAdapter = OrderAdapter(emptyList())

        recyclerView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            // Optional: Add item decoration for spacing
            val dividerItemDecoration = DividerItemDecoration(this@HistoryActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun loadOrderData() {
        // ข้อมูลตัวอย่าง - แทนที่ด้วยข้อมูลจริงของคุณ
        val sampleOrders = listOf(
            Order(
                id = "001",
                dateTime = "15 มี.ค. 2567 14:30",
                status = "เสร็จสิ้น",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_green_dark),
                menu = "โกโก้",
                menuSize = "เล็ก",
                toppings = "ท็อปปิ้ง: มาร์ชแมลโล่, วิปครีม",
                imageResource = R.drawable.logo_nono
            ),
            Order(
                id = "002",
                dateTime = "15 มี.ค. 2567 13:45",
                status = "กำลังทำ",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_orange_dark),
                menu = "กาแฟลาเต้",
                menuSize = "กลาง",
                toppings = "ท็อปปิ้ง: ซีรัปวานิลลา, นมฟองนุ่ม",
                imageResource = R.drawable.logo_nono
            ),
            Order(
                id = "003",
                dateTime = "15 มี.ค. 2567 12:20",
                status = "รอการชำระเงิน",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_red_dark),
                menu = "ชาเขียว",
                menuSize = "ใหญ่",
                toppings = "ท็อปปิ้ง: บอลไข่มุก, วิปครีม, น้ำตาลทราย",
                imageResource = R.drawable.logo_nono
            ),
            Order(
                id = "004",
                dateTime = "14 มี.ค. 2567 16:15",
                status = "เสร็จสิ้น",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_green_dark),
                menu = "เอสเปรสโซ่",
                menuSize = "เล็ก",
                toppings = "ท็อปปิ้ง: ไม่มี",
                imageResource = R.drawable.logo_nono
            ),
            Order(
                id = "005",
                dateTime = "14 มี.ค. 2567 11:30",
                status = "ยกเลิก",
                statusColor = ContextCompat.getColor(this, android.R.color.darker_gray),
                menu = "คาปูชิโน่",
                menuSize = "กลาง",
                toppings = "ท็อปปิ้ง: ผงโกโก้, วิปครีม",
                imageResource = R.drawable.logo_nono
            ),
            Order(
                id = "006",
                dateTime = "13 มี.ค. 2567 09:00",
                status = "เสร็จสิ้น",
                statusColor = ContextCompat.getColor(this, android.R.color.holo_green_dark),
                menu = "ชาไทย",
                menuSize = "ใหญ่",
                toppings = "ท็อปปิ้ง: นมสด, น้ำแข็งเยอะ",
                imageResource = R.drawable.logo_nono
            )
        )

        orderAdapter.updateOrders(sampleOrders)
    }

    // Function to simulate adding new order (สำหรับทดสอบ)
    private fun addNewOrder() {
        val newOrder = Order(
            id = System.currentTimeMillis().toString(),
            dateTime = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("th", "TH")).format(Date()),
            status = "รอการยืนยัน",
            statusColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark),
            menu = "เมนูใหม่",
            menuSize = "กลาง",
            toppings = "ท็อปปิ้ง: ตามสั่ง",
            imageResource = R.drawable.logo_nono
        )

        orderAdapter.addOrder(newOrder)
        recyclerView.smoothScrollToPosition(0) // Scroll to top to show new item
    }
}