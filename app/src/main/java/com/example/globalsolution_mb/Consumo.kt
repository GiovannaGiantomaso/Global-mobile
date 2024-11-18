package com.example.globalsolution_mb

import java.util.Date

data class Consumo(
    val dataRegistro: Date,
    val consumoKwh: Double,
    val id: String = ""
)
