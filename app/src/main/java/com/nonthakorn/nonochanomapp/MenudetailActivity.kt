package com.nonthakorn.nonochanomapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenudetailActivity : BaseActivity() {

    private lateinit var drinkNameTh: TextView
    private lateinit var drinkNameEn: TextView
    private lateinit var drinkDescription: TextView
    private lateinit var sizeTitle: TextView
    private lateinit var sizeSubtitle: TextView
    private lateinit var toppingTitle: TextView
    private lateinit var toppingSubtitle: TextView
    private lateinit var notesTitle: TextView
    private lateinit var notesEdittext: EditText
    private lateinit var orderButton: Button
    private lateinit var productImage: ImageView

    private lateinit var radioSmall: RadioButton
    private lateinit var radioMedium: RadioButton
    private lateinit var radioLarge: RadioButton
    private lateinit var priceSmall: TextView
    private lateinit var priceMedium: TextView
    private lateinit var priceLarge: TextView

    private lateinit var radioBubble: RadioButton
    private lateinit var radioJelly: RadioButton

    // Product data from intent
    private var productId: String = ""
    private var productNameEn: String = ""
    private var productNameTh: String = ""
    private var productPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menudetail)

        initViews()
        setupClickListeners()
        loadProductData()
        updateContentByLanguage()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        drinkNameTh = findViewById(R.id.drink_name_th)
        drinkNameEn = findViewById(R.id.drink_name_en)
        drinkDescription = findViewById(R.id.drink_description)
        sizeTitle = findViewById(R.id.size_title)
        sizeSubtitle = findViewById(R.id.size_subtitle)
        toppingTitle = findViewById(R.id.topping_title)
        toppingSubtitle = findViewById(R.id.topping_subtitle)
        notesTitle = findViewById(R.id.notes_title)
        notesEdittext = findViewById(R.id.notes_edittext)
        orderButton = findViewById(R.id.order_button)
        productImage = findViewById(R.id.product_image)

        radioSmall = findViewById(R.id.radio_small)
        radioMedium = findViewById(R.id.radio_medium)
        radioLarge = findViewById(R.id.radio_large)
        priceSmall = findViewById(R.id.price_small)
        priceMedium = findViewById(R.id.price_medium)
        priceLarge = findViewById(R.id.price_large)

        radioBubble = findViewById(R.id.radio_bubble)
        radioJelly = findViewById(R.id.radio_jelly)
    }

    private fun setupClickListeners() {
        val backButton: ImageButton = findViewById(R.id.back_button)

        backButton.setOnClickListener {
            finish()
        }

        orderButton.setOnClickListener {
            processOrder()
        }
    }

    private fun loadProductData() {
        // Get data from intent
        productId = intent.getStringExtra("product_id") ?: "1"
        productNameEn = intent.getStringExtra("product_name_en") ?: "Coconut"
        productNameTh = intent.getStringExtra("product_name_th") ?: "มะพร้าว"
        productPrice = intent.getDoubleExtra("product_price", 45.0)

        // Set product image based on ID
        val imageResource = when (productId) {
            "1" -> R.drawable.product01
            "2" -> R.drawable.product02
            "3" -> R.drawable.product03
            "4" -> R.drawable.product04
            "5" -> R.drawable.product05
            "6" -> R.drawable.product06
            "7" -> R.drawable.product07
            "8" -> R.drawable.product08
            "9" -> R.drawable.product09
            "10" -> R.drawable.product10
            else -> R.drawable.product01
        }
        productImage.setImageResource(imageResource)

        // Set product names
        if (LocaleHelper.isEnglishLanguage(this)) {
            drinkNameTh.text = productNameEn
            drinkNameEn.text = productNameEn.lowercase()
        } else {
            drinkNameTh.text = productNameTh
            drinkNameEn.text = productNameEn
        }
    }

    private fun updateContentByLanguage() {
        val isEnglish = LocaleHelper.isEnglishLanguage(this)

        if (isEnglish) {
            drinkDescription.text = "You can choose the style"
            sizeTitle.text = "Cup Size"
            sizeSubtitle.text = "Please select 1 option"
            toppingTitle.text = "Toppings"
            toppingSubtitle.text = "Select maximum 1 option"
            notesTitle.text = "Additional Notes"
            notesEdittext.hint = "Please specify additional information"
            orderButton.text = "Order Now"

            radioSmall.text = "Small Cup"
            radioMedium.text = "Medium Cup"
            radioLarge.text = "Large Cup"

            radioBubble.text = "Add Bubble"
            radioJelly.text = "Add Jelly"

            // Update prices with English format
            priceSmall.text = "${(productPrice - 10).toInt()} Baht"
            priceMedium.text = "${productPrice.toInt()} Baht"
            priceLarge.text = "${(productPrice + 15).toInt()} Baht"

        } else {
            drinkDescription.text = "สามารถเลือกรูปแบบได้"
            sizeTitle.text = "ขนาดแก้ว"
            sizeSubtitle.text = "กรุณาเลือก 1 ข้อ"
            toppingTitle.text = "ท็อปปิ้ง"
            toppingSubtitle.text = "เลือกสูงสุด 1 ข้อ"
            notesTitle.text = "เพิ่มเติม"
            notesEdittext.hint = "กรุณาระบุข้อมูลเพิ่มเติม"
            orderButton.text = "กดสั่งซื้อ"

            radioSmall.text = "แก้วเล็ก"
            radioMedium.text = "แก้วกลาง"
            radioLarge.text = "แก้วใหญ่"

            radioBubble.text = "เพิ่มไข่มุก"
            radioJelly.text = "เพิ่มเยลลี่"

            // Update prices with Thai format
            priceSmall.text = "${(productPrice - 10).toInt()} บาท"
            priceMedium.text = "${productPrice.toInt()} บาท"
            priceLarge.text = "${(productPrice + 15).toInt()} บาท"
        }
    }

    private fun processOrder() {
        val isEnglish = LocaleHelper.isEnglishLanguage(this)

        // Get selected size
        val selectedSize = when {
            radioSmall.isChecked -> if (isEnglish) "Small" else "เล็ก"
            radioMedium.isChecked -> if (isEnglish) "Medium" else "กลาง"
            radioLarge.isChecked -> if (isEnglish) "Large" else "ใหญ่"
            else -> if (isEnglish) "Small" else "เล็ก"
        }

        // Get selected topping
        val selectedTopping = when {
            radioBubble.isChecked -> if (isEnglish) "Toppings: Bubble" else "ท็อปปิ้ง: ไข่มุก"
            radioJelly.isChecked -> if (isEnglish) "Toppings: Jelly" else "ท็อปปิ้ง: เยลลี่"
            else -> if (isEnglish) "Toppings: None" else "ท็อปปิ้ง: ไม่มี"
        }

        // Get additional notes
        val notes = notesEdittext.text.toString()

        // Calculate final price
        val finalPrice = when {
            radioSmall.isChecked -> productPrice - 5
            radioMedium.isChecked -> productPrice
            radioLarge.isChecked -> productPrice + 10
            else -> productPrice
        }

        // Show order summary
        val orderSummary = if (isEnglish) {
            "Order Summary:\n" +
                    "Menu: ${productNameEn}\n" +
                    "Size: $selectedSize\n" +
                    "$selectedTopping\n" +
                    "Price: ${finalPrice.toInt()} Baht" +
                    if (notes.isNotEmpty()) "\nNotes: $notes" else ""
        } else {
            "สรุปการสั่งซื้อ:\n" +
                    "เมนู: ${productNameTh}\n" +
                    "ขนาด: $selectedSize\n" +
                    "$selectedTopping\n" +
                    "ราคา: ${finalPrice.toInt()} บาท" +
                    if (notes.isNotEmpty()) "\nหมายเหตุ: $notes" else ""
        }

        // Show confirmation dialog
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(if (isEnglish) "Confirm Order" else "ยืนยันคำสั่งซื้อ")
        builder.setMessage(orderSummary)
        builder.setPositiveButton(if (isEnglish) "Confirm" else "ยืนยัน") { _, _ ->
            // Process the order
            val successMessage = if (isEnglish) {
                "Order placed successfully!"
            } else {
                "สั่งซื้อสำเร็จแล้ว!"
            }
            Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show()

            // Go to history page
            val intent = Intent(this, HistoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(if (isEnglish) "Cancel" else "ยกเลิก") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        // Update content when returning to this activity
        updateContentByLanguage()
    }
}