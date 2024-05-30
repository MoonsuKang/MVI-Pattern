package org.now.sopt.mvi_pattern.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("urls")
    val urls: UrlResponse,
    @SerializedName("color")
    val color: String,
)