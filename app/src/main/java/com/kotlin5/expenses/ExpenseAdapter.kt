package com.kotlin5.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin5.expenses.databinding.CashtransectionBinding

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    private var expenses = listOf<Expense>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = CashtransectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    override fun getItemCount(): Int = expenses.size

    fun submitList(list: List<Expense>) {
        expenses = list
        notifyDataSetChanged()
    }

    inner class ExpenseViewHolder(private val binding: CashtransectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            binding.etName.text = expense.name
            binding.etDate.text = expense.date

            val formattedAmount = String.format("%.2f", expense.amount)
            val amountText = when (expense.type) {
                "Cash In" -> "+$$formattedAmount"
                "Cash Out" -> "-$$formattedAmount"
                else -> "$$formattedAmount"
            }

            binding.etAmount.text = amountText

            val color = when (expense.type) {
                "Cash In" -> binding.root.context.getColor(R.color.colorCashIn)
                "Cash Out" -> binding.root.context.getColor(R.color.colorCashOut)
                else -> binding.root.context.getColor(R.color.colorDefault)
            }

            binding.etAmount.setTextColor(color)
        }

    }
}
