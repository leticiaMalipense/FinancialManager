package br.edu.ifsp.scl.financialmanager.enums

enum class Classification(val id: Int, val description: String) {

    ALIMENTACAO(1,"Alimentação"),
    SAUDE(2,"Saúde"),
    TRANSPORTE(3,"Transporte"),
    MORADIA(4,"Moradia"),
    EDUCACAO(5,"Educação"),
    LAZER(6,"Lazer"),
    TARIFAS(7,"Tarifas"),
    BANCARIAS(8,"Bancárias"),
    LUZ(9,"Luz"),
    AGUA(10,"Água"),
    TELEFONE(11,"Telefone"),
    OUTRO(12  ,"Outro");

    override fun toString(): String {
        return description
    }

    companion object {
        fun getEnumFromDescription(description: String): Classification{
            for (value in Classification.values()) {
                if (value.description.equals(description)) {
                    return value
                }
            }
            return OUTRO
        }
        fun getEnumFromId(id: Int): Classification{
            for (value in Classification.values()) {
                if (value.id.compareTo(id) == 0) {
                    return value
                }
            }
            return OUTRO
        }
    }

}
