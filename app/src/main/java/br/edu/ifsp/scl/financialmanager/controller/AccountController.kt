package br.edu.ifsp.scl.financialmanager.controller

import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.view.AccountActivity

class AccountController(val view: AccountActivity) {

    val model: AccountService

    init{
        //Instanciando o service
        model = AccountService(view.applicationContext)
    }

    //Metodo para criação da account
    fun createAccount(account: Account) {
        val id = model.create(account)
        account.id = id
    }

    //Metodo para deletar a account
    fun deleteAccount(accountId: Int) {
        model.delete(accountId)
    }

    //Metodo que retorna a lista de accounts cadastradas
    fun findAllAccount(): List<Account> {
        return model.findAll()
    }

    fun findByName(name: String): Account {
        return model.findByName(name)
    }
}