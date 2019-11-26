package br.edu.ifsp.scl.financialmanager.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper(context: Context) : SQLiteOpenHelper(context, "financial.manager.db", null, 1) {


    object Constants {
        //Constantes para nomear as tabelas, seguindo o padrão de começar com o prefixo TB
        const val TABLE_ACCOUNT = "TB_ACCOUNT"
        const val TABLE_TRANSACTION = "TB_TRANSACTION"

        //Constantes dos nomes dos campos das tabelas
        const val KEY_ID = "ID"
        const val KEY_DESCRIPTION = "DESCRIPTION"
        const val KEY_VALUE = "VALUE"
        const val KEY_ACCOUNT_ID = "ACCOUNT_ID"
        const val KEY_DATE_TRANSACTION = "DATE_TRANSACTION"
        const val KEY_TYPE_TRANSACTION = "TYPE_TRANSACTION"
        const val KEY_CLASSIFICATION_ID = "CLASSIFICATION_ID"
        const val KEY_PERIOD_ID = "PERIOD_ID"
    }

    //Script para a criação da tabela de accounts
    val CREATE_TABLE_ACCOUNT = (" CREATE TABLE IF NOT EXISTS "+ Constants.TABLE_ACCOUNT +" ( "+ Constants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.KEY_DESCRIPTION +" TEXT, "+ Constants.KEY_VALUE+" NUMERIC )")

    //Script para a criação da tabela de transaction
    val CREATE_TABLE_TRASACTION = (" CREATE TABLE IF NOT EXISTS "+ Constants.TABLE_TRANSACTION +" ( "+ Constants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.KEY_DESCRIPTION +" TEXT, "+ Constants.KEY_VALUE+" NUMERIC, "+ Constants.KEY_ACCOUNT_ID +" INTEGER, "
            +  Constants.KEY_DATE_TRANSACTION +" INTEGER, "+ Constants.KEY_TYPE_TRANSACTION +" INTEGER, "
            + Constants.KEY_CLASSIFICATION_ID + " TEXT, " + Constants.KEY_PERIOD_ID + " INTEGER )")


    //Metodo onCreate sobrescrito para adicionar o codigo de criação das tabelas
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT)
        sqLiteDatabase.execSQL(CREATE_TABLE_TRASACTION)
    }

    //OnUpdate vazio pois ainda não estamos trabalhando com diferentes versões do aplicativo
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {

    }

}