package com.nonthakorn.nonochanomapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // ค้นหา Button และ TextView จาก ID ที่กำหนดไว้ใน XML
        val mainmenu_button: Button = findViewById(R.id.mainmenu_button)
        val SigninText: TextView = findViewById(R.id.Sign_in_Text)

        // ตั้งค่าการคลิกสำหรับปุ่ม
        mainmenu_button.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยัง HomeActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // ตั้งค่าการคลิกสำหรับข้อความ
        SigninText.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยัง HomeActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}