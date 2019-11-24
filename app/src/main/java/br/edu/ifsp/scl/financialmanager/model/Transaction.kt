package br.edu.ifsp.scl.financialmanager.model

import android.os.Parcel
import android.os.Parcelable
import br.edu.ifsp.scl.financialmanager.enums.Classification
import br.edu.ifsp.scl.financialmanager.enums.Period

data class Transaction(var id: Int, var description: String, var value: Double) : Parcelable {

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

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return super.toString()
    }
}




