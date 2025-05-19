package com.example.sport_app.Fragments

import android.os.Parcel
import android.os.Parcelable

data class Product (

    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int
): Parcelable {
    //Second Builder
    constructor(parcel: Parcel) : this (
        parcel.readInt(),
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readDouble(),
        parcel.readInt(),

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeInt(imageResId)
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
