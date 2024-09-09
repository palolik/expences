import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotlin5.expenses.Expense
import com.kotlin5.expenses.ExpenseViewModel
import com.kotlin5.expenses.databinding.AddexpencesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExpenseActivity : AppCompatActivity() {
    private lateinit var binding: AddexpencesBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddexpencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val type = binding.expenseType.text.toString()
            val name = binding.expenseName.text.toString()
            val amount = binding.expenseAmount.text.toString().toDoubleOrNull() ?: 0.0
            val date = binding.expenseDate.text.toString()
            val time = binding.expenseTime.text.toString()
            val newExpense = Expense(
                name = name,
                amount = amount,
                date = date,
                time = time,
                type = type
            )
            expenseViewModel.insertExpense(newExpense)
            finish()
        }
    }
}
