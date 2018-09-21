package com.yggdralisk.myapplication.data.model

import android.os.Parcel
import android.os.Parcelable

data class DataModel(
        val data: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createStringArrayList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(data)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel = DataModel(parcel)

        override fun newArray(size: Int): Array<DataModel?> = arrayOfNulls(size)
    }
}