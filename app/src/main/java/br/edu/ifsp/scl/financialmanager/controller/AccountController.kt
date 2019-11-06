package br.edu.ifsp.scl.financialmanager.controller

import br.edu.ifsp.scl.financialmanager.Service.AccountService
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.view.AccountActivity

class AccountController(val view: AccountActivity) {

    val model: AccountService

    init{
        model = AccountService(view.applicationContext)
    }

    fun createAccount(account: Account) {
        model.create(account)
        //view.atualizaView(account)
    }

    fun deleteAccount(accountId: Int) {
        model.delete(accountId)
        //view.atualizaView(account)
    }

    fun findAllAccount() {
        model.findAll()
    }
}