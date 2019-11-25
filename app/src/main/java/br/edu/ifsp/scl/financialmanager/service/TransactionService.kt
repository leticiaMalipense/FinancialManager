package br.edu.ifsp.scl.financialmanager.service

import android.content.Context
import br.edu.ifsp.scl.financialmanager.data.AccountDao
import br.edu.ifsp.scl.financialmanager.data.AccountDaoImpl
import br.edu.ifsp.scl.financialmanager.data.TransactionDao
import br.edu.ifsp.scl.financialmanager.data.TransactionDaoImpl
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Transaction

class TransactionService(context: Context) {

    var transactionDao: TransactionDao
    var accountDao: AccountDao

    init{
        transactionDao = TransactionDaoImpl(context)
        accountDao = AccountDaoImpl(context)

    }

    fun create(transaction: Transaction): Int {
        val account = accountDao.findById(transaction.accountId)

        if(transaction.typeTransaction == TransactionType.CREDITO.id){
            var currentBalance = account.value + transaction.value
            accountDao.updateBalance(account.id, currentBalance)
        }else{
            var currentBalance = account.value - transaction.value
            accountDao.updateBalance(account.id, currentBalance)
        }
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

}