package com.ravirawal.iw_assignment.model_ui

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

object OrderedItemDiffCallBack : DiffUtil.ItemCallback<OrderedItem>() {
    override fun areItemsTheSame(
        oldItem: OrderedItem,
        newItem: OrderedItem
    ) = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: OrderedItem,
        newItem: OrderedItem
    ) = oldItem == newItem
}

data class OrderedItem(
    val extra: String?,
    val name: String?,
    val price: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(extra)
        parcel.writeString(name)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderedItem> {
        override fun createFromParcel(parcel: Parcel): OrderedItem {
            return OrderedItem(parcel)
        }

        override fun newArray(size: Int): Array<OrderedItem?> {
            return arrayOfNulls(size)
        }
    }

}