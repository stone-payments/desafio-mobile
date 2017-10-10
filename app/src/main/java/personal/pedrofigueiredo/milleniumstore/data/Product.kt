package personal.pedrofigueiredo.milleniumstore.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(val title:String,val price:Int, val seller:String, val thumb:String) : Parcelable