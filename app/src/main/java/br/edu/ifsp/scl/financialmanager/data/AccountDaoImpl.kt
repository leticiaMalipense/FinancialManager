package br.edu.ifsp.scl.financialmanager.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.edu.ifsp.scl.financialmanager.model.Account
import kotlinx.android.synthetic.main.activity_account.*
import java.sql.SQLException

class AccountDaoImpl(context: Context) : AccountDao {

    val NOME_BD = "financial.manager.db"
    val MODO_BD = Context.MODE_PRIVATE

    val ACCOUNT_TABLE = ("CREATE TABLE IF NOT EXISTS ACCOUNT ( ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, VALUE NUMERIC )")

    val sqliteBd: SQLiteDatabase
    init{
        sqliteBd = context.openOrCreateDatabase(NOME_BD, MODO_BD, null)
        try {
            sqliteBd.execSQL(ACCOUNT_TABLE)
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun create(account: Account) {

        var query = "INSERT INTO ACCOUNT (DESCRIPTION, VALUE) VALUES ('${account.description}' , ${account.value});"

        try {
            sqliteBd.execSQL(query)
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun delete(id: Int) {
        var query = "DELETE FROM ACCOUNT WHERE ID = ${id};"

        try {
            sqliteBd.execSQL(query)
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun findAll(): List<Account> {
        var accounts: ArrayList<Account> = ArrayList()
        var query = "SELECT * FROM ACCOUNT WHERE 1=1;"

        try {
            val cursor = sqliteBd.rawQuery(query, arrayOf())
            while (cursor.moveToNext()){
                val description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                val value = cursor.getDouble(cursor.getColumnIndex("VALUE"));

                val account: Account = Account(description, value)
                accounts.add(account)
            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
        }

        return accounts
    }

}
