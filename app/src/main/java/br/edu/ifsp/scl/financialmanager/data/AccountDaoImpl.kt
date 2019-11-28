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
        //Instancia o sqlHelper
        dbHelper = SqlHelper(context)
    }

    //Metodo responsavél por realizar a criação da account
    override fun create(account: Account): Int {
        //Cria conexão com o banco de dados
        database =  dbHelper.getReadableDatabase()

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
            //fecha conexão
            database.close()
        }

        return 0;

    }

    //Metodo para realização a exclusão da account
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

    //Metodo que realiza o somatorio SUM do saldo de todas as contas, devolvendo o saldo total atual
    override fun getCurrentBalance(): Double {
        database =  dbHelper.getReadableDatabase();

        try {
            //SUM operação de soma da query
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

    //Metodo que retorna a account apartir do ID da mesma
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

    override fun findByName(name: String): Account {
        database =  dbHelper.getReadableDatabase();

        var account: Account = Account()

        try {
            val cursor = database.query(SqlHelper.Constants.TABLE_ACCOUNT, null, "upper(" + SqlHelper.Constants.KEY_DESCRIPTION +") " + "='" + name.toUpperCase() + "'",null, null, null, null)
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

    //Metodo que recupera todas as contas cadastradas
    override fun findAll(): List<Account> {
        database =  dbHelper.getReadableDatabase();

        var accounts: ArrayList<Account> = ArrayList()

        try {
            val cursor = database.query(SqlHelper.Constants.TABLE_ACCOUNT, null, null,null, null, null, null)
            //Montar lista de account com base do resultado da query realizada
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

    //Metodo para realizar o update da account
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
