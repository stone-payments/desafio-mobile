package com.partiufast.mercadodoimperador.model

import android.os.Parcel
import android.os.Parcelable

class BillHistory(val date: String,val customerName: String,val cardLastNumbers: Int, val value: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<BillHistory> = object : Parcelable.Creator<BillHistory> {
            override fun createFromParcel(source: Parcel): BillHistory = BillHistory(source)
            override fun newArray(size: Int): Array<BillHistory?> = arrayOfNulls(size)
        }
    }

    fun getAlertString(): String {
        return value +" às "+ date +'\n'+ customerName + "\nCartão x"+ cardLastNumbers
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readInt(),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(date)
        dest.writeString(customerName)
        dest.writeInt(cardLastNumbers)
        dest.writeString(value)
    }
}
