package com.kotlin5.expenses.ui.view

import android.content.Intent
import android.os.Bundle

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        // Initialize bottom navigation
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

        // Set default selected item
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.wallet
        }

        // Inflate layouts
        cardsLayout = layoutInflater.inflate(R.layout.cards_layout, null)
        accountsLayout = layoutInflater.inflate(R.layout.accounts_layout, null)

        // Initialize chip group
        chipGroup = findViewById(R.id.chipGroup)

        // Restore saved state
        if (savedInstanceState != null) {
            selectedChipId = savedInstanceState.getInt("selected_chip_id", R.id.cards)
            chipGroup.check(selectedChipId)
        }


        val manualMoneyView = accountsLayout.findViewById<View>(R.id.manualmoney)
        manualMoneyView.setOnClickListener {
            val intent = Intent(this, AddMoneyActivity::class.java)
            startActivity(intent)
        }

        // Set up chip group listener
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

        // Show initial layout
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
