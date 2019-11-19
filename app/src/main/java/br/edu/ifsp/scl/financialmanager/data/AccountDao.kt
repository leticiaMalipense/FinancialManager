package br.edu.ifsp.scl.financialmanager.data

import br.edu.ifsp.scl.financialmanager.model.Account

interface AccountDao {

    fun create(account: Account) : Int
    fun delete(id: Int)
    fun findAll() : List<Account>

}