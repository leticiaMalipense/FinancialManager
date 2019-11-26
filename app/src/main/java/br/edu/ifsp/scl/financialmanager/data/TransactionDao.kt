package br.edu.ifsp.scl.financialmanager.data

import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.TransactionType
import br.edu.ifsp.scl.financialmanager.model.Transaction

interface TransactionDao {

    fun create(transaction: Transaction) : Int
    fun delete(id: Int)
    fun deleteAllByAccount(accountId: Int)
    fun findAll() : List<Transaction>
    fun findByArgs(transactionTypeId: Int?, classificationId: Int?): List<Transaction>

}