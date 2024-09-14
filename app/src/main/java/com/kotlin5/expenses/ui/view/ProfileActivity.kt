package com.kotlin5.expenses.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kotlin5.expenses.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.statistics -> {
                    startActivity(Intent(this, StatisticsActivity::class.java))
                    true
                }
                R.id.wallet -> {
                    startActivity(Intent(this, WalletActivity::class.java))
                    true
                }
                R.id.myprofile -> {
                    true
                }
                else -> false
            }
        }
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.myprofile
        }
    }
}