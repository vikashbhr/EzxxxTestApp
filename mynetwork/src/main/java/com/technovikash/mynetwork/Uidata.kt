package com.technovikash.mynetwork


import com.google.gson.annotations.SerializedName

data class Uidata(
    @SerializedName("hint")
    val hint: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("uitype")
    val uitype: String?,
    @SerializedName("value")
    val value: String?
)