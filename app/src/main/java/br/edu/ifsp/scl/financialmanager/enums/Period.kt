package br.edu.ifsp.scl.financialmanager.enums

enum class Period(val id: Int, val description: String) {

    NUNCA(1,"Nunca"),
    DIARIAMENTE(2,"Diariamente"),
    SEMANALMENTE(3,"Semanalmente"),
    QUINZENALMENTE(4,"Quinzenalmente"),
    MENSALMENTE(5,"Mensalmente"),
    BIMESTRALMENTE(6,"Bimestralmente"),
    TRIMESTRALMENTE(7,"Trimestralmente"),
    SEMESTRALMENTE(8,"Semestralmente"),
    ANUALMENTE(9,"Anualmente");

    override fun toString(): String {
        return description
    }

}