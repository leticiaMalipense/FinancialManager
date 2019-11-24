package br.edu.ifsp.scl.financialmanager.data

import br.edu.ifsp.scl.financialmanager.model.Transaction

interface TransactionDao {

    fun create(account: Transaction) : Int
    fun delete(id: Int)
    fun deleteAllByAccount(accountId: Int)
    fun findAll() : List<Transaction>

}