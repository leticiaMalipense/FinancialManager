package br.edu.ifsp.scl.financialmanager.data

import br.edu.ifsp.scl.financialmanager.model.Transaction

interface TransactionDao {

    fun create(transaction: Transaction) : Int
    fun delete(id: Int)
    fun deleteAllByAccount(accountId: Int)
    fun findAll() : List<Transaction>
    fun findByArgs(transactionTypeId: Int?, classificationId: Int?): List<Transaction>
    fun findByDate(accountId: Int, beginDate: String?, endDate: String?): List<Transaction>

}