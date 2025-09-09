package com.nonthakorn.nonochanomapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LanguageswtichActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_languageswtich)

        // ค้นหา Button และ TextView จาก ID ที่กำหนดไว้ใน XML

        val btnback: ImageView = findViewById(R.id.btnback)

        // ตั้งค่าการคลิกสำหรับปุ่ม
        btnback.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยัง HomeActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}