package br.edu.ifsp.scl.financialmanager.controller

import br.edu.ifsp.scl.financialmanager.model.Transaction
import br.edu.ifsp.scl.financialmanager.service.TransactionService
import br.edu.ifsp.scl.financialmanager.view.TransactionActivity


class TransactionController(val view: TransactionActivity) {

    val model: TransactionService

    init{
        model = TransactionService(view.applicationContext)
    }

    //Metodo para criar a transação
    fun createTransaction(transaction: Transaction) {
        model.create(transaction)
    }

    //Metodo que retorna todas as transações cadastradas
    fun findAllTransaction(): List<Transaction> {
        return model.findAll()
    }
}