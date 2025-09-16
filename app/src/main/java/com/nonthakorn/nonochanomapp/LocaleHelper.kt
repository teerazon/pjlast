package com.nonthakorn.nonochanomapp

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleHelper {

    private const val SELECTED_LANGUAGE = "selected_language"
    private const val DEFAULT_LANGUAGE = "th"

    // ฟังก์ชันสำหรับตั้งค่าภาษา
    fun setLocale(context: Context, language: String): Context {
        saveLanguage(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else {
            updateResourcesLegacy(context, language)
        }
    }

    // บันทึกภาษาที่เลือกลง SharedPreferences
    private fun saveLanguage(context: Context, language: String) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString(SELECTED_LANGUAGE, language).apply()
    }

    // ดึงภาษาที่บันทึกไว้
    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getString(SELECTED_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

    // อัพเดทภาษาสำหรับ Android N ขึ้นไป
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    // อัพเดทภาษาสำหรับ Android เวอร์ชันเก่า
    @Suppress("DEPRECATION")
    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }

        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    // เช็คว่าเป็นภาษาไทยหรือไม่
    fun isThaiLanguage(context: Context): Boolean {
        return getLanguage(context) == "th"
    }

    // เช็คว่าเป็นภาษาอังกฤษหรือไม่
    fun isEnglishLanguage(context: Context): Boolean {
        return getLanguage(context) == "en"
    }
}