package com.br.data.response

import com.beust.klaxon.Json

data class ChampionResponse(
    @Json(name = "country")
    val champion: String,
    @Json(name = "year")
    val cupYear: Int
)