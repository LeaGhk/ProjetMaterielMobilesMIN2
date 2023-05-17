package fr.epf.mm.cinemathome

import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)
