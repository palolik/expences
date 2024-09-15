package com.kotlin5.expenses.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.kotlin5.expenses.R

class ConnectwalletActivity : AppCompatActivity() {

    private lateinit var cardsLayout: View
    private lateinit var accountsLayout: View
    private lateinit var chipGroup: ChipGroup
    private var selectedChipId: Int = R.id.cards

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connectwallet)
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
                R.id.wallet -> true
                R.id.myprofile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.wallet
        }
        cardsLayout = layoutInflater.inflate(R.layout.cards_layout, null)
        accountsLayout = layoutInflater.inflate(R.layout.accounts_layout, null)

        chipGroup = findViewById(R.id.chipGroup)

        if (savedInstanceState != null) {
            selectedChipId = savedInstanceState.getInt("selected_chip_id", R.id.cards)
            chipGroup.check(selectedChipId)
        }

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.cards -> {
                    showLayout(cardsLayout)
                }
                R.id.btn_cash_out -> {
                    showLayout(accountsLayout)
                }
            }
            selectedChipId = checkedId
        }

        showLayout(
            if (selectedChipId == R.id.cards) cardsLayout else accountsLayout
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_chip_id", selectedChipId)
    }

    private fun showLayout(layout: View) {
        val layoutContainer: ViewGroup = findViewById(R.id.layoutContainer)
        layoutContainer.removeAllViews()
        layoutContainer.addView(layout)
    }
}
