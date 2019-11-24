package br.edu.ifsp.scl.financialmanager.enums

import java.io.Serializable

enum class Classification(val id: Int, val description: String): Serializable {

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
    ETC(12  ,"Outro");

    override fun toString(): String {
        return description
    }

}
