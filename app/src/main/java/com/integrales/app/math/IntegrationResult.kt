package com.integrales.app.math

data class IntegrationResult(
    val originalFunction: String,
    val result: String,
    val method: String,
    val steps: List<String>,
    val isDefinite: Boolean,
    val numericValue: Double = 0.0
)

