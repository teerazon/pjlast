package com.nonthakorn.nonochanomapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        val language = LocaleHelper.getLanguage(newBase ?: return)
        val context = LocaleHelper.setLocale(newBase, language)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ตั้งค่าภาษาทุกครั้งที่สร้าง Activity
        val language = LocaleHelper.getLanguage(this)
        LocaleHelper.setLocale(this, language)
    }

    // ฟังก์ชันสำหรับเปลี่ยนภาษาและ restart activity
    protected fun changeLanguage(languageCode: String) {
        LocaleHelper.setLocale(this, languageCode)
        recreate() // สร้าง activity ใหม่เพื่อให้ภาษาเปลี่ยน
    }

    // ฟังก์ชันสำหรับรีสตาร์ทแอพ
    protected fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent?.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}