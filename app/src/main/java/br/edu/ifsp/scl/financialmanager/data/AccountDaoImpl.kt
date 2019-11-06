package br.edu.ifsp.scl.financialmanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.edu.ifsp.scl.financialmanager.model.Account
import java.sql.SQLException

class AccountDaoImpl(context: Context) : AccountDao {

    var dbHelper: SqlHelper
    var database: SQLiteDatabase

    init{
        dbHelper = SqlHelper(context)
        database =  dbHelper.getReadableDatabase();

    }

    override fun create(account: Account) {
        database =  dbHelper.getReadableDatabase();

        val values = ContentValues()
        values.put(SqlHelper.Constants.KEY_DESCRIPTION, account.description)
        values.put(SqlHelper.Constants.KEY_VALUE, account.value)

        try {
            val id = database.insert(SqlHelper.Constants.TABLE_ACCOUNT, null, values)
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
        finally {
            database.close()
        }

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

}
