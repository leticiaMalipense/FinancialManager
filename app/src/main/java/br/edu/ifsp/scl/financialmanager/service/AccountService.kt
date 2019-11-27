package br.edu.ifsp.scl.financialmanager.service

import android.content.Context
import br.edu.ifsp.scl.financialmanager.data.AccountDao
import br.edu.ifsp.scl.financialmanager.data.AccountDaoImpl
import br.edu.ifsp.scl.financialmanager.data.TransactionDao
import br.edu.ifsp.scl.financialmanager.data.TransactionDaoImpl
import br.edu.ifsp.scl.financialmanager.model.Account

class AccountService(context: Context) {

    var accountDao: AccountDao
    var transactionDao: TransactionDao

    //Instanciando services
    init {
        accountDao = AccountDaoImpl(context)
        transactionDao = TransactionDaoImpl(context)
    }

    fun create(account: Account): Int {
        return accountDao.create(account)
    }

    fun delete(accountId: Int) {
        accountDao.delete(accountId)
        transactionDao.deleteAllByAccount(accountId)
    }

    fun getCurrentBalance(): Double {
        return accountDao.getCurrentBalance()
    }

    fun findById(accountId: Int): Account {
        return accountDao.findById(accountId)
    }

    fun findAll(): List<Account> {
        return accountDao.findAll()
    }

    fun update(account: Account) {
        return accountDao.update(account)
    }

    fun findByName(name: String): Account {
        return accountDao.findByName(name)
    }

}