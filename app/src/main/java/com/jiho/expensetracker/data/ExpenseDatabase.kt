package com.jiho.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration

@Database(entities = [Expense::class], version = 4, exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun expenseDAO(): ExpenseDAO

    companion object{
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                )
                    .fallbackToDestructiveMigration(true)
                    //todo get rid of this before final
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}