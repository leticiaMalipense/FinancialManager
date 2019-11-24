package br.edu.ifsp.scl.financialmanager.model

import android.os.Parcel
import android.os.Parcelable


data class Account(var id: Int, var description: String, var value: Double) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        if (parcel != null) {
            parcel.writeInt(id)
            parcel.writeString(description)
            parcel.writeDouble(value)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account> {
        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return this.description ?: "Sem Descrição"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + description.hashCode()
        return result
    }


}



