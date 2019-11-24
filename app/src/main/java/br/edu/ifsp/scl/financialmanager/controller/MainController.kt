package br.edu.ifsp.scl.financialmanager.controller

import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.view.MainActivity

class MainController(val view: MainActivity) {

    val model: AccountService

    init{
        model = AccountService(view.applicationContext)
    }

    fun findAllAccount(): List<Account> {
        return model.findAll()
    }
}