package com.kotlin5.expenses

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideExpenseDatabase(context: Context): ExpenseDatabase {
return Room.databaseBuilder(
    context,
    ExpenseDatabase::class.java,
    "expense_database"
).build()
    }
    @Provides
    fun provideExpenseDao(db: ExpenseDatabase): ExpenseDao {
        return db.expenseDao()
    }
}