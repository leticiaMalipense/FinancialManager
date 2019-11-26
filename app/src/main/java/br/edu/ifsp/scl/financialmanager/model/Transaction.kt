package br.edu.ifsp.scl.financialmanager.model

import android.os.Parcel
import android.os.Parcelable

//Modelo que representa a Transação
data class Transaction(var id: Int, var description: String, var value: Double, var accountId: Int,
                       var transactionDate: String, var typeTransaction: Int, var classificationId: Int, var periodId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )


    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        if (parcel != null) {
            parcel.writeInt(id)
            parcel.writeString(description)
            parcel.writeDouble(value)
            parcel.writeInt(accountId)
            parcel.writeString(transactionDate)
            parcel.writeInt(classificationId)
            parcel.writeInt(periodId)
            parcel.writeInt(typeTransaction)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return this.description
    }
}




