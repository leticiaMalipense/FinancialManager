package br.edu.ifsp.scl.financialmanager.controller

import android.app.Activity
import br.edu.ifsp.scl.financialmanager.Service.AccountService
import br.edu.ifsp.scl.financialmanager.model.Account

class AccountController(val view: Activity) {

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

    fun findAllAccount(): List<Account> {
        return model.findAll()
    }
}