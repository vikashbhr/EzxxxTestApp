package com.technovikash.mynetwork


import com.google.gson.annotations.SerializedName

data class CustomUiResponse(
    @SerializedName("heading-text")
    val headingText: String?,
    @SerializedName("logo-url")
    val logoUrl: String?,
    @SerializedName("uidata")
    val uidata: ArrayList<Uidata?>?
)