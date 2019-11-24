package br.edu.ifsp.scl.financialmanager.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper(context: Context) : SQLiteOpenHelper(context, "financial.manager.db", null, 1) {


    object Constants {
        const val TABLE_ACCOUNT = "ACCOUNT"
        const val TABLE_TRANSACTION = "TRANSACTION"

        const val KEY_ID = "ID"
        const val KEY_DESCRIPTION = "DESCRIPTION"
        const val KEY_VALUE = "VALUE"
        const val KEY_ACCOUNT_ID = "ACCOUNT_ID"
        const val KEY_CLASSIFICATION_ID = "CLASSIFICATION_ID"
        const val KEY_PERIOD_ID = "PERIOD_ID"
    }

    val CREATE_TABLE_ACCOUNT = (" CREATE TABLE IF NOT EXISTS "+ Constants.TABLE_ACCOUNT +" ( "+ Constants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.KEY_DESCRIPTION +" TEXT, "+ Constants.KEY_VALUE+" NUMERIC )")


    val CREATE_TABLE_TRASACTION = (" CREATE TABLE IF NOT EXISTS "+ Constants.TABLE_TRANSACTION +" ( "+ Constants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.KEY_DESCRIPTION +" TEXT, "+ Constants.KEY_VALUE+" NUMERIC "+ Constants.KEY_ACCOUNT_ID +" INTEGER "
            +  Constants.KEY_CLASSIFICATION_ID +" INTEGER "+ Constants.KEY_PERIOD_ID +" INTEGER )")


    val CREATE_TABLE = CREATE_TABLE_ACCOUNT + CREATE_TABLE_TRASACTION;


    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {

    }

}