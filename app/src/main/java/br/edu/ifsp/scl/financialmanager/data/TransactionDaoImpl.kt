package br.edu.ifsp.scl.financialmanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.model.Transaction
import java.sql.SQLException

class TransactionDaoImpl(context: Context) : TransactionDao {
    var dbHelper: SqlHelper
    lateinit var database: SQLiteDatabase

    init{
        dbHelper = SqlHelper(context)
    }

    override fun create(transaction: Transaction): Int {
        database =  dbHelper.getReadableDatabase();

        val values = ContentValues()
        values.put(SqlHelper.Constants.KEY_DESCRIPTION, transaction.description)
        values.put(SqlHelper.Constants.KEY_VALUE, transaction.value)
        values.put(SqlHelper.Constants.KEY_ACCOUNT_ID, transaction.accountId)
        values.put(SqlHelper.Constants.KEY_DATE_TRANSACTION, transaction.transactionDate)
        values.put(SqlHelper.Constants.KEY_TYPE_TRANSACTION, transaction.typeTransaction)
        values.put(SqlHelper.Constants.KEY_CLASSIFICATION_ID, transaction.classificationId)
        values.put(SqlHelper.Constants.KEY_PERIOD_ID, transaction.periodId)

        try {
            val id = database.insert(SqlHelper.Constants.TABLE_TRANSACTION, null, values)
            return id.toInt()
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
        finally {
            database.close()
        }

        return 0;

    }

    override fun delete(id: Int) {
        database =  dbHelper.getReadableDatabase();

        try {
            database.delete(SqlHelper.Constants.TABLE_TRANSACTION,SqlHelper.Constants.KEY_ID + "=" + id, null )
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
        finally {
            database.close()
        }

    }

    override fun deleteAllByAccount(accountId: Int) {
        database =  dbHelper.getReadableDatabase();

        try {
            database.delete(SqlHelper.Constants.TABLE_TRANSACTION,SqlHelper.Constants.KEY_ACCOUNT_ID + "=" + accountId, null )
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
        finally {
            database.close()
        }

    }

    override fun findAll(): List<Transaction> {
        database =  dbHelper.getReadableDatabase();

        var transactions: ArrayList<Transaction> = ArrayList()

        try {
            val cursor = database.query(SqlHelper.Constants.TABLE_TRANSACTION, null, null,null, null, null, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val description = cursor.getString(1)
                val value = cursor.getDouble(2)
                val accountId = cursor.getInt(3)
                val transactionDate = cursor.getString(4)
                val typeTransaction = cursor.getInt(5)
                val classificationId = cursor.getInt(6)
                val periodId = cursor.getInt(7)

                val transaction = Transaction(id, description, value, accountId, transactionDate, typeTransaction, classificationId, periodId)
                transactions.add(transaction)

            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }finally {
            database.close()
        }

        return transactions
    }

    override fun findByArgs(transactionTypeId: Int?, classificationId: Int?): List<Transaction> {
        database =  dbHelper.getReadableDatabase();

        var transactions: ArrayList<Transaction> = ArrayList()

        try {
            var where = ""
            if (transactionTypeId != null) {
                where = SqlHelper.Constants.KEY_TYPE_TRANSACTION + "=" + transactionTypeId
            } else if (classificationId != null) {
                where = SqlHelper.Constants.KEY_CLASSIFICATION_ID + "=" + classificationId
            }

            val cursor = database.query(SqlHelper.Constants.TABLE_TRANSACTION, null,
                where,null, null, null, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val description = cursor.getString(1)
                val value = cursor.getDouble(2)
                val accountId = cursor.getInt(3)
                val transactionDate = cursor.getString(4)
                val typeTransaction = cursor.getInt(5)
                val classificationId = cursor.getInt(6)
                val periodId = cursor.getInt(7)

                val transaction = Transaction(id, description, value, accountId, transactionDate, typeTransaction, classificationId, periodId)
                transactions.add(transaction)

            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }finally {
            database.close()
        }

        return transactions
    }

}
