package za.co.saweather.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import za.co.saweather.R
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val switchNotifications: Switch = findViewById(R.id.switchNotifications)
        val btnEnglish: Button = findViewById(R.id.btnEnglish)
        val btnZulu: Button = findViewById(R.id.btnZulu)

        // Load saved settings
        switchNotifications.isChecked = prefs.getBoolean("notifications_enabled", true)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            Toast.makeText(this, getString(R.string.notifications_updated), Toast.LENGTH_SHORT).show()
        }

        btnEnglish.setOnClickListener { setLocale("en") }
        btnZulu.setOnClickListener { setLocale("zu") }
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        prefs.edit().putString("app_language", language).apply()

        Toast.makeText(this, getString(R.string.language_changed), Toast.LENGTH_SHORT).show()

        // Restart app to apply language
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
