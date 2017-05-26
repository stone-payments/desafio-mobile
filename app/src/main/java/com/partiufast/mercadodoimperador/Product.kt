package com.partiufast.mercadodoimperador

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

class Product(val title: String, val price: BigDecimal, val zipcode: String, val seller: String, val thumbnailHd: String, val date: String, var productCount: Int) : Parcelable {
    //TODO: Formatar valor do Big Decimal
    fun copy(title: String = this.title, price: BigDecimal = this.price, seller: String = this.seller,
             thumbnailHd: String = this.thumbnailHd, date: String = this.date, productCount: Int = this.productCount) = Product(title, price, zipcode, seller, thumbnailHd, date, productCount)

    fun addCountProduct() {
        productCount++
    }

    fun removeCountProduct() {
        productCount--
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Product> = object : Parcelable.Creator<Product> {
            override fun createFromParcel(source: Parcel): Product = Product(source)
            override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readSerializable() as BigDecimal, source.readString(), source.readString(), source.readString(), source.readString(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeSerializable(price)
        dest?.writeString(zipcode)
        dest?.writeString(seller)
        dest?.writeString(thumbnailHd)
        dest?.writeString(date)
        dest?.writeInt(productCount)
    }
}
