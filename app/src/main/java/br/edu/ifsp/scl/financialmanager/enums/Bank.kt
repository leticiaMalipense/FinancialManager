package br.edu.ifsp.scl.financialmanager.enums

//Enum para representar os tipos de transação (Crédito ou Débito)
enum class Bank(val id: Int, val description: String, val icon: String) {
    AMAZONIA(1,"Amazonia", "ic_banco_amazonia"),
    BANESTES(2,"Banestes", "ic_banco_banestes"),
    BANK_BOSTON(3,"Bank Boston", "ic_banco_bank_boston"),
    BANRISUL(4,"Banrisul", "ic_banco_barinsul"),
    BRADESCO(5,"Bradesco", "ic_banco_bradesco"),
    DO_BRASIL(6,"Banco do Brasil", "ic_banco_brasil"),
    BRASILIA(7,"Brasilia", "ic_banco_brasilia"),
    CITI_BANK(8,"Citi Bank", "ic_banco_citi_bank"),
    HSBC(9,"HSBC", "ic_banco_hsbc"),
    INTER(10,"Banco Inter", "ic_banco_inter"),
    ITAU(11,"ITAU", "ic_banco_itau"),
    NORDESTE(12,"Banco Nordeste", "ic_banco_nordeste"),
    NU_BANK(13,"NuBank", "ic_banco_nubank"),
    ORIGINAL(14,"Original", "ic_banco_original"),
    REAL(15,"Banco Real", "ic_banco_real"),
    SANTANDER(16,"Santander", "ic_banco_santander"),
    SICOOB(17,"Sicoob", "ic_banco_sicoob"),
    SICREDI(18,"Sicred", "ic_banco_sicredi"),
    OUTRO(19,"Outro", "ic_banco_outro");

    override fun toString(): String {
        return description
    }

    companion object {
        //Metodo para recuperar objeto apartir do atributo description
        fun getEnumFromDescription(description: String): Bank{
            for (value in Bank.values()) {
                if (value.description.toUpperCase().equals(description.toUpperCase())) {
                    return value
                }
            }
            return OUTRO
        }

        fun getEnumFromId(id: Int): Bank{
            for (value in Bank.values()) {
                if (value.id.compareTo(id) == 0) {
                    return value
                }
            }
            return OUTRO
        }
    }
}
