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
            binding.ammount.text = "${expense.amount}--"
            binding.ammountw.text = expense.name
            binding.time.text = expense.time
            binding.date.text = expense.date

            if (expense.type == "Income") {
                binding.star1.setImageResource(R.drawable.`in`)
            } else {
                binding.star1.setImageResource(R.drawable.out)
            }
        }
    }
}
