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
            binding.etAmount.text = when (expense.type) {
                "Cash In" -> "+$${String.format("%.2f", expense.amount)}"
                "Cash Out" -> "-$${String.format("%.2f", expense.amount)}"
                else -> "$${String.format("%.2f", expense.amount)}"
            }
        }
    }
}
