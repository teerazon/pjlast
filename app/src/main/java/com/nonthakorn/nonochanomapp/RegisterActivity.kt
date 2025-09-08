package com.nonthakorn.nonochanomapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // ค้นหา Button และ TextView จาก ID ที่กำหนดไว้ใน XML
        val btncreate: Button = findViewById(R.id.btncreate)
        val loginText: TextView = findViewById(R.id.Login_text)

        // ตั้งค่าการคลิกสำหรับปุ่ม "ลงทะเบียน"
        btncreate.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยังหน้า HomeActivity (สมมติว่าหลังจากลงทะเบียนสำเร็จจะไปที่หน้าหลัก)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // ตั้งค่าการคลิกสำหรับข้อความ "เข้าสู่ระบบ"
        loginText.setOnClickListener {
            // สร้าง Intent เพื่อย้ายกลับไปยังหน้า LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}