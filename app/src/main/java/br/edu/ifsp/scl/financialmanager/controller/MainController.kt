package br.edu.ifsp.scl.financialmanager.controller

import br.edu.ifsp.scl.financialmanager.service.AccountService
import br.edu.ifsp.scl.financialmanager.model.Account
import br.edu.ifsp.scl.financialmanager.view.MainActivity

class MainController(val view: MainActivity) {

    val model: AccountService

    init{
        //Instanciando o service
        model = AccountService(view.applicationContext)
    }

    //Metodo para buscar todas as accounts cadastrads do service
    fun findAllAccount(): List<Account> {
        return model.findAll()
    }

    //Metodo que retornar account apartir do ID
    fun findById(accountId: Int): Account {
        return model.findById(accountId)
    }

    //Metodo que retorna o saldo total (Somatorio de todas as contas) atualizado
    fun getCurrentBalance(): Double {
        return model.getCurrentBalance()
    }

    fun delete(accountId: Int) {
        model.delete(accountId)
    }

}