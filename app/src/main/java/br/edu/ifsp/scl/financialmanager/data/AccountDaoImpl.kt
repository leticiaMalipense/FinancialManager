package br.edu.ifsp.scl.financialmanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.edu.ifsp.scl.financialmanager.model.Account
import java.sql.SQLException

class AccountDaoImpl(context: Context) : AccountDao {

    var dbHelper: SqlHelper
    lateinit var database: SQLiteDatabase

    init{
        dbHelper = SqlHelper(context)
    }

    override fun create(account: Account): Int {
        database =  dbHelper.getReadableDatabase();

        val values = ContentValues()
        values.put(SqlHelper.Constants.KEY_DESCRIPTION, account.description)
        values.put(SqlHelper.Constants.KEY_VALUE, account.value)

        try {
            val id = database.insert(SqlHelper.Constants.TABLE_ACCOUNT, null, values)
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
            database.delete(SqlHelper.Constants.TABLE_ACCOUNT,SqlHelper.Constants.KEY_ID + "=" + id, null )
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
        finally {
            database.close()
        }

    }


    override fun getCurrentBalance(): Double {
        database =  dbHelper.getReadableDatabase();

        try {
            val cursor = database.rawQuery("select sum(value) from "+ SqlHelper.Constants.TABLE_ACCOUNT, null);
            while (cursor.moveToNext()) {
                return cursor.getDouble(0)
            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }finally {
            database.close()
        }

        return 0.0
    }


    override fun updateBalance(accountId: Int, value: Double) {
        database =  dbHelper.getReadableDatabase();

        try {
            val values = ContentValues()
            values.put(SqlHelper.Constants.KEY_VALUE, value)

            database.update(
                SqlHelper.Constants.TABLE_ACCOUNT, values, SqlHelper.Constants.KEY_ID+ "=" + accountId, null
            )
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
        finally {
            database.close()
        }

    }

    override fun findById(accountId: Int): Account {
        database =  dbHelper.getReadableDatabase();

        lateinit var account: Account

        try {
            val cursor = database.query(SqlHelper.Constants.TABLE_ACCOUNT, null, SqlHelper.Constants.KEY_ID+ "=" + accountId,null, null, null, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val description = cursor.getString(1)
                val value = cursor.getDouble(2)

                account = Account(id, description, value)
            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }finally {
            database.close()
        }

        return account
    }

    override fun findAll(): List<Account> {
        database =  dbHelper.getReadableDatabase();

        var accounts: ArrayList<Account> = ArrayList()

        try {
            val cursor = database.query(SqlHelper.Constants.TABLE_ACCOUNT, null, null,null, null, null, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val description = cursor.getString(1)
                val value = cursor.getDouble(2)

                val account = Account(id, description, value)
                accounts.add(account)

            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }finally {
            database.close()
        }

        return accounts
    }

    override fun findByDescription(description: String): Account? {
        database =  dbHelper.getReadableDatabase();

        var account: Account? = null

        try {
            val whereClause = SqlHelper.Constants.KEY_DESCRIPTION + " = ?"
            val whereArgs = arrayOf(description)

            val cursor = database.query(SqlHelper.Constants.TABLE_ACCOUNT, null, whereClause,whereArgs, null, null, null)
            if (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val desc = cursor.getString(1)
                val value = cursor.getDouble(2)

                account = Account(id, desc, value)

            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }finally {
            database.close()
        }

        return account
    }

    override fun update(account: Account) {
        database =  dbHelper.getReadableDatabase();

        val values = ContentValues()
        values.put(SqlHelper.Constants.KEY_DESCRIPTION, account.description)
        values.put(SqlHelper.Constants.KEY_VALUE, account.value)

        database.update(SqlHelper.Constants.TABLE_ACCOUNT,
            values,
            SqlHelper.Constants.KEY_ID + "=" + account.id,
            null)
        database.close()
    }

}
