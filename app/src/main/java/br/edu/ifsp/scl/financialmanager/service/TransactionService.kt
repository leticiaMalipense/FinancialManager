package br.edu.ifsp.scl.financialmanager.service

import android.content.Context
import br.edu.ifsp.scl.financialmanager.data.AccountDao
import br.edu.ifsp.scl.financialmanager.data.AccountDaoImpl
import br.edu.ifsp.scl.financialmanager.data.TransactionDao
import br.edu.ifsp.scl.financialmanager.data.TransactionDaoImpl
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Transaction

class TransactionService(context: Context) {

    var transactionDao: TransactionDao
    var accountDao: AccountDao

    //Instanciando services
    init{
        transactionDao = TransactionDaoImpl(context)
        accountDao = AccountDaoImpl(context)

    }

    fun create(transaction: Transaction): Int {
        return transactionDao.create(transaction)
    }

    fun delete(transactionId: Int) {
        transactionDao.delete(transactionId)
    }

    fun deleteAllByAccount(accountId: Int) {
        transactionDao.deleteAllByAccount(accountId)
    }


    fun findAll(): List<Transaction> {
        return transactionDao.findAll()
    }

    fun findByTransactionType(transactionTypeId: Int) : List<Transaction> {
        return transactionDao.findByArgs(transactionTypeId, null)
    }

    fun findByTransactionClassification(classificationId: Int) : List<Transaction> {
        return transactionDao.findByArgs(null, classificationId)
    }

    fun findByDate(accountId: Int, beginDate: String?, endDate: String?): List<Transaction> {
        return transactionDao.findByDate(accountId, beginDate, endDate)
    }
}
