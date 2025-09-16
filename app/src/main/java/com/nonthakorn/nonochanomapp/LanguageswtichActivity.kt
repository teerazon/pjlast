package com.nonthakorn.nonochanomapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LanguageswtichActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_languageswtich)

        // ค้นหา Button และ ImageView จาก ID ที่กำหนดไว้ใน XML
        val btnback: ImageView = findViewById(R.id.btnback)
        val english_button: Button = findViewById(R.id.english_button)
        val thai_button: Button = findViewById(R.id.thai_button)

        // ตั้งค่าการคลิกสำหรับปุ่มกลับ
        btnback.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ตั้งค่าการคลิกสำหรับปุ่มภาษาไทย
        thai_button.setOnClickListener {
            changeLanguageAndGoToMenu("th")
        }

        // ตั้งค่าการคลิกสำหรับปุ่มภาษาอังกฤษ
        english_button.setOnClickListener {
            changeLanguageAndGoToMenu("en")
        }

        // ตั้งค่าสีปุ่มตามภาษาปัจจุบัน
        updateButtonStates()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun changeLanguageAndGoToMenu(languageCode: String) {
        // เปลี่ยนภาษา
        LocaleHelper.setLocale(this, languageCode)

        // ไปหน้า Menu และ clear stack
        val intent = Intent(this, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun updateButtonStates() {
        val currentLanguage = LocaleHelper.getLanguage(this)
        val thai_button: Button = findViewById(R.id.thai_button)
        val english_button: Button = findViewById(R.id.english_button)

        if (currentLanguage == "th") {
            // ภาษาไทยถูกเลือก
            thai_button.alpha = 1.0f
            english_button.alpha = 0.6f
        } else {
            // ภาษาอังกฤษถูกเลือก
            thai_button.alpha = 0.6f
            english_button.alpha = 1.0f
        }
    }
}