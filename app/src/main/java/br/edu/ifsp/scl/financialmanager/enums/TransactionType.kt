package br.edu.ifsp.scl.financialmanager.enums

enum class TransactionType(val id: Int, val description: String) {
    CREDITO(1,"Crédito"),
    DEBITO(2,"Débito");

    override fun toString(): String {
        return description
    }

    companion object {
        fun getEnumFromDescription(description: String): TransactionType{
            for (value in TransactionType.values()) {
                if (value.description.equals(description)) {
                    return value
                }
            }
            return CREDITO
        }
    }
}
