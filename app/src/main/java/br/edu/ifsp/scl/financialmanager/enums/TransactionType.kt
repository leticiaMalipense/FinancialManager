package br.edu.ifsp.scl.financialmanager.enums

//Enum para representar os tipos de transação (Crédito ou Débito)
enum class TransactionType(val id: Int, val description: String) {
    CREDITO(1,"Crédito"),
    DEBITO(2,"Débito");

    override fun toString(): String {
        return description
    }

    companion object {
        //Metodo para recuperar objeto apartir do atributo description
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
