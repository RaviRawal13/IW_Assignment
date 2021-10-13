package com.ravirawal.iw_assignment.network.model

import com.google.gson.annotations.SerializedName

typealias OrderedItems = List<OrdersListResponse.Data.Item?>?

data class OrdersListResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("items")
        val items: List<Item?>?
    ) {
        data class Item(
            @SerializedName("extra")
            val extra: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("price")
            val price: String?
        )
    }
}