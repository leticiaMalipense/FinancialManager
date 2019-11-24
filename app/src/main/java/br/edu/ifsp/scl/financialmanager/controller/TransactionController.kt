package br.edu.ifsp.scl.financialmanager.controller

import br.edu.ifsp.scl.financialmanager.model.Transaction
import br.edu.ifsp.scl.financialmanager.service.TransactionService
import br.edu.ifsp.scl.financialmanager.view.TransactionActivity


class TransactionController(val view: TransactionActivity) {

    val model: TransactionService

    init{
        model = TransactionService(view.applicationContext)
    }

    fun createTransaction(transaction: Transaction) {
        val id = model.create(transaction)
    }

    fun findAllTransaction(): List<Transaction> {
        return model.findAll()
    }
}