package br.edu.ifsp.scl.financialmanager.service

import android.content.Context
import br.edu.ifsp.scl.financialmanager.data.TransactionDao
import br.edu.ifsp.scl.financialmanager.data.TransactionDaoImpl
import br.edu.ifsp.scl.financialmanager.model.Transaction

class TransactionService(context: Context) {

    var transactionDao: TransactionDao

    init{
        transactionDao = TransactionDaoImpl(context)

    }

    fun create(transaction: Transaction): Int {
        return transactionDao.create(transaction)
    }

    fun delete(transactionId: Int) {
        transactionDao.delete(transactionId)
    }

    fun findAll(): List<Transaction> {
        return transactionDao.findAll()
    }

}