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

class UserinfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_userinfo)

        // ค้นหา Button และ TextView จาก ID ที่กำหนดไว้ใน XML
        val nav_home: ImageView = findViewById(R.id.nav_home)
        val nav_history: ImageView = findViewById(R.id.nav_history)
        val icon_flag: ImageView = findViewById(R.id.icon_flag)
        val logout_user: TextView = findViewById(R.id.logout_user)

        // ตั้งค่าการคลิกสำหรับปุ่ม
        nav_home.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยัง HomeActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // ตั้งค่าการคลิกสำหรับข้อความ
        nav_history.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยัง HomeActivity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        icon_flag.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยัง HomeActivity
            val intent = Intent(this, LanguageswtichActivity::class.java)
            startActivity(intent)
        }

        logout_user.setOnClickListener {
            // สร้าง Intent เพื่อย้ายไปยัง HomeActivity
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