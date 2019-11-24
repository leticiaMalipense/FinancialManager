package br.edu.ifsp.scl.financialmanager.controller

import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.view.AccountActivity

class AccountController(val view: AccountActivity) {

    val model: AccountService

    init{
        model = AccountService(view.applicationContext)
    }

    fun createAccount(account: Account) {
        val id = model.create(account)
        account.id = id
        view.refreshView(account)
    }

    fun deleteAccount(accountId: Int) {
        model.delete(accountId)
    }

    fun findAllAccount(): List<Account> {
        return model.findAll()
    }
}