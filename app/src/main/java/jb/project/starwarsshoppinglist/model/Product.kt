package jb.project.starwarsshoppinglist.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Jb on 12/10/2017.
 */

data class Product(val title: String? = null,
                   val price: Int? = null,
                   val zipcode: String? = null,
                   val seller: String? = null,
                   val thumbnailHd: String? = null,
                   val date: String? = null,
                   val type: String? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeValue(price)
        parcel.writeString(zipcode)
        parcel.writeString(seller)
        parcel.writeString(thumbnailHd)
        parcel.writeString(date)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}

