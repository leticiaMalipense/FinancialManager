package br.edu.ifsp.scl.financialmanager.enums

enum class TransectionType(val id: Int, val description: String) {
    CREDITO(1,"Crédito"),
    DEBITO(2,"Débito");

    override fun toString(): String {
        return description
    }
}
