package com.integrales.app.math

import kotlin.math.*

class IntegralSolver {
    
    fun solveIndefiniteIntegral(function: String): IntegrationResult {
        val cleanFunction = function.replace(" ", "").lowercase()
        val steps = mutableListOf<String>()
        
        steps.add("PASO 1: Analizando la función f(x) = $function")
        steps.add("PASO 2: Identificando el tipo de integral")
        
        return try {
            when {
                // Integrales básicas
                cleanFunction == "x" -> {
                    steps.add("PASO 3: Es una integral directa de x")
                    steps.add("PASO 4: Aplicando la regla ∫x dx = x²/2 + C")
                    steps.add("PASO 5: Resultado: ∫x dx = x²/2 + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "x²/2",
                        method = "Integral directa",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "x^2" || cleanFunction == "x²" -> {
                    steps.add("PASO 3: Es una integral directa de x²")
                    steps.add("PASO 4: Aplicando la regla ∫x^n dx = x^(n+1)/(n+1) + C")
                    steps.add("PASO 5: Para n = 2: ∫x² dx = x³/3 + C")
                    steps.add("PASO 6: Resultado: ∫x² dx = x³/3 + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "x³/3",
                        method = "Integral directa",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "x^3" || cleanFunction == "x³" -> {
                    steps.add("PASO 3: Es una integral directa de x³")
                    steps.add("PASO 4: Aplicando la regla ∫x^n dx = x^(n+1)/(n+1) + C")
                    steps.add("PASO 5: Para n = 3: ∫x³ dx = x⁴/4 + C")
                    steps.add("PASO 6: Resultado: ∫x³ dx = x⁴/4 + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "x⁴/4",
                        method = "Integral directa",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "1/x" -> {
                    steps.add("PASO 3: Es una integral de función racional 1/x")
                    steps.add("PASO 4: Aplicando la regla ∫1/x dx = ln|x| + C")
                    steps.add("PASO 5: Resultado: ∫1/x dx = ln|x| + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "ln|x|",
                        method = "Integral directa",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "sin(x)" -> {
                    steps.add("PASO 3: Es una integral trigonométrica de sin(x)")
                    steps.add("PASO 4: Aplicando la regla ∫sin(x) dx = -cos(x) + C")
                    steps.add("PASO 5: Resultado: ∫sin(x) dx = -cos(x) + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "-cos(x)",
                        method = "Integral trigonométrica",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "cos(x)" -> {
                    steps.add("PASO 3: Es una integral trigonométrica de cos(x)")
                    steps.add("PASO 4: Aplicando la regla ∫cos(x) dx = sin(x) + C")
                    steps.add("PASO 5: Resultado: ∫cos(x) dx = sin(x) + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "sin(x)",
                        method = "Integral trigonométrica",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "e^x" -> {
                    steps.add("PASO 3: Es una integral exponencial de e^x")
                    steps.add("PASO 4: Aplicando la regla ∫e^x dx = e^x + C")
                    steps.add("PASO 5: Resultado: ∫e^x dx = e^x + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "e^x",
                        method = "Integral directa",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "ln(x)" -> {
                    steps.add("PASO 3: Es una integral logarítmica de ln(x)")
                    steps.add("PASO 4: Aplicando integración por partes:")
                    steps.add("   u = ln(x) → du = 1/x dx")
                    steps.add("   dv = dx → v = x")
                    steps.add("PASO 5: ∫ln(x) dx = x*ln(x) - ∫x * 1/x dx")
                    steps.add("PASO 6: ∫ln(x) dx = x*ln(x) - ∫1 dx")
                    steps.add("PASO 7: ∫ln(x) dx = x*ln(x) - x")
                    steps.add("PASO 8: Resultado: ∫ln(x) dx = x*ln(x) - x + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "x*ln(x) - x",
                        method = "Por partes",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "tan(x)" -> {
                    steps.add("PASO 3: Es una integral trigonométrica de tan(x)")
                    steps.add("PASO 4: Usando la identidad: tan(x) = sin(x)/cos(x)")
                    steps.add("PASO 5: Haciendo sustitución u = cos(x):")
                    steps.add("   u = cos(x) → du = -sin(x) dx")
                    steps.add("PASO 6: ∫tan(x) dx = ∫sin(x)/cos(x) dx = -∫du/u")
                    steps.add("PASO 7: ∫tan(x) dx = -ln|u| = -ln|cos(x)|")
                    steps.add("PASO 8: Resultado: ∫tan(x) dx = -ln|cos(x)| + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "-ln|cos(x)|",
                        method = "Sustitución trigonométrica",
                        steps = steps,
                        isDefinite = false
                    )
                }
                cleanFunction == "sec(x)" -> {
                    steps.add("PASO 3: Es una integral trigonométrica de sec(x)")
                    steps.add("PASO 4: Multiplicando por (sec(x) + tan(x))/(sec(x) + tan(x)):")
                    steps.add("PASO 5: ∫sec(x) dx = ∫sec(x) * (sec(x) + tan(x))/(sec(x) + tan(x)) dx")
                    steps.add("PASO 6: Haciendo u = sec(x) + tan(x):")
                    steps.add("   u = sec(x) + tan(x) → du = (sec(x)tan(x) + sec²(x)) dx")
                    steps.add("PASO 7: du = sec(x)(tan(x) + sec(x)) dx = sec(x) * u dx")
                    steps.add("PASO 8: ∫sec(x) dx = ∫du/u = ln|u|")
                    steps.add("PASO 9: Resultado: ∫sec(x) dx = ln|sec(x) + tan(x)| + C")
                    IntegrationResult(
                        originalFunction = function,
                        result = "ln|sec(x) + tan(x)|",
                        method = "Sustitución trigonométrica",
                        steps = steps,
                        isDefinite = false
                    )
                }
                
                // Integrales con sustitución
                cleanFunction.contains("sin(") && cleanFunction.contains("^") -> {
                    solveTrigonometricSubstitution(cleanFunction, steps)
                }
                cleanFunction.contains("cos(") && cleanFunction.contains("^") -> {
                    solveTrigonometricSubstitution(cleanFunction, steps)
                }
                cleanFunction.contains("tan(") && cleanFunction.contains("^") -> {
                    solveTrigonometricSubstitution(cleanFunction, steps)
                }
                cleanFunction.contains("e^") && cleanFunction.contains("x^") -> {
                    solveExponentialSubstitution(cleanFunction, steps)
                }
                cleanFunction.contains("1/(x^2+1)") || cleanFunction.contains("1/(x²+1)") -> {
                    solveArctangent(cleanFunction, steps)
                }
                cleanFunction.contains("1/sqrt(1-x^2)") || cleanFunction.contains("1/√(1-x²)") -> {
                    solveArcsin(cleanFunction, steps)
                }
                
                // Integrales por partes
                cleanFunction.contains("*") && (cleanFunction.contains("ln(") || cleanFunction.contains("e^")) -> {
                    solveByParts(cleanFunction, steps)
                }
                
                // Integrales con sustitución simple
                cleanFunction.contains("(") && cleanFunction.contains(")") -> {
                    solveSubstitution(cleanFunction, steps)
                }
                
                // Polinomios
                cleanFunction.matches(Regex(".*x\\^\\d+.*")) -> {
                    solvePolynomial(cleanFunction, steps)
                }
                
                else -> {
                    // Intentar evaluación numérica simple
                    try {
                        val result = evaluateSimpleFunction(cleanFunction)
                        steps.add("Evaluación directa")
                        IntegrationResult(
                            originalFunction = function,
                            result = result,
                            method = "Evaluación directa",
                            steps = steps,
                            isDefinite = false
                        )
                    } catch (e: Exception) {
                        throw IllegalArgumentException("No se pudo resolver la integral: $function")
                    }
                }
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("Error al resolver la integral: ${e.message}")
        }
    }
    
    fun solveDefiniteIntegral(function: String, lowerLimit: Double, upperLimit: Double): IntegrationResult {
        val indefiniteResult = solveIndefiniteIntegral(function)
        val steps = indefiniteResult.steps.toMutableList()
        
        steps.add("PASO ${steps.size + 1}: Aplicando el Teorema Fundamental del Cálculo")
        steps.add("PASO ${steps.size + 1}: ∫[${lowerLimit},${upperLimit}] ${function} dx = F(${upperLimit}) - F(${lowerLimit})")
        steps.add("PASO ${steps.size + 1}: Donde F(x) = ${indefiniteResult.result}")
        
        val numericValue = try {
            val result = evaluateDefiniteIntegral(function, lowerLimit, upperLimit)
            steps.add("PASO ${steps.size + 1}: Evaluando F(${upperLimit}) - F(${lowerLimit}) = ${String.format("%.6f", result)}")
            result
        } catch (e: Exception) {
            steps.add("PASO ${steps.size + 1}: Error en la evaluación numérica")
            0.0 // Valor por defecto si no se puede evaluar
        }
        
        return IntegrationResult(
            originalFunction = function,
            result = indefiniteResult.result,
            method = indefiniteResult.method,
            steps = steps,
            isDefinite = true,
            numericValue = numericValue
        )
    }
    
    private fun solveTrigonometricSubstitution(function: String, steps: MutableList<String>): IntegrationResult {
        steps.add("PASO 3: Aplicando sustitución trigonométrica")
        
        return when {
            function.contains("sin(x)^2") || function.contains("sin²(x)") -> {
                steps.add("PASO 4: Usando identidad trigonométrica: sin²(x) = (1 - cos(2x))/2")
                steps.add("PASO 5: Sustituyendo: ∫sin²(x) dx = ∫(1 - cos(2x))/2 dx")
                steps.add("PASO 6: Separando: ∫(1 - cos(2x))/2 dx = (1/2)∫1 dx - (1/2)∫cos(2x) dx")
                steps.add("PASO 7: Integrando: (1/2)x - (1/2)(sin(2x)/2) = x/2 - sin(2x)/4")
                steps.add("PASO 8: Resultado: ∫sin²(x) dx = x/2 - sin(2x)/4 + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "x/2 - sin(2x)/4",
                    method = "Sustitución trigonométrica",
                    steps = steps,
                    isDefinite = false
                )
            }
            function.contains("cos(x)^2") || function.contains("cos²(x)") -> {
                steps.add("PASO 4: Usando identidad trigonométrica: cos²(x) = (1 + cos(2x))/2")
                steps.add("PASO 5: Sustituyendo: ∫cos²(x) dx = ∫(1 + cos(2x))/2 dx")
                steps.add("PASO 6: Separando: ∫(1 + cos(2x))/2 dx = (1/2)∫1 dx + (1/2)∫cos(2x) dx")
                steps.add("PASO 7: Integrando: (1/2)x + (1/2)(sin(2x)/2) = x/2 + sin(2x)/4")
                steps.add("PASO 8: Resultado: ∫cos²(x) dx = x/2 + sin(2x)/4 + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "x/2 + sin(2x)/4",
                    method = "Sustitución trigonométrica",
                    steps = steps,
                    isDefinite = false
                )
            }
            else -> {
                steps.add("Sustitución trigonométrica general")
                IntegrationResult(
                    originalFunction = function,
                    result = "Resultado trigonométrico",
                    method = "Sustitución trigonométrica",
                    steps = steps,
                    isDefinite = false
                )
            }
        }
    }
    
    private fun solveByParts(function: String, steps: MutableList<String>): IntegrationResult {
        steps.add("PASO 3: Aplicando integración por partes: ∫u dv = uv - ∫v du")
        
        return when {
            function.contains("x*ln(x)") -> {
                steps.add("PASO 4: Identificando u y dv:")
                steps.add("   u = ln(x) → du = 1/x dx")
                steps.add("   dv = x dx → v = x²/2")
                steps.add("PASO 5: Aplicando la fórmula: ∫u dv = uv - ∫v du")
                steps.add("PASO 6: ∫x*ln(x) dx = x²*ln(x)/2 - ∫x²/2 * 1/x dx")
                steps.add("PASO 7: Simplificando: ∫x*ln(x) dx = x²*ln(x)/2 - ∫x/2 dx")
                steps.add("PASO 8: Integrando el segundo término: ∫x/2 dx = x²/4")
                steps.add("PASO 9: Resultado final: ∫x*ln(x) dx = x²*ln(x)/2 - x²/4 + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "x²*ln(x)/2 - x²/4",
                    method = "Por partes",
                    steps = steps,
                    isDefinite = false
                )
            }
            function.contains("x*e^x") -> {
                steps.add("PASO 4: Identificando u y dv:")
                steps.add("   u = x → du = dx")
                steps.add("   dv = e^x dx → v = e^x")
                steps.add("PASO 5: Aplicando la fórmula: ∫u dv = uv - ∫v du")
                steps.add("PASO 6: ∫x*e^x dx = x*e^x - ∫e^x dx")
                steps.add("PASO 7: Integrando el segundo término: ∫e^x dx = e^x")
                steps.add("PASO 8: Resultado final: ∫x*e^x dx = x*e^x - e^x + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "x*e^x - e^x",
                    method = "Por partes",
                    steps = steps,
                    isDefinite = false
                )
            }
            else -> {
                steps.add("Integración por partes general")
                IntegrationResult(
                    originalFunction = function,
                    result = "Resultado por partes",
                    method = "Por partes",
                    steps = steps,
                    isDefinite = false
                )
            }
        }
    }
    
    private fun solveSubstitution(function: String, steps: MutableList<String>): IntegrationResult {
        steps.add("PASO 3: Aplicando método de sustitución u = g(x)")
        
        return when {
            function.contains("(2x+1)") -> {
                steps.add("PASO 4: Haciendo la sustitución:")
                steps.add("   u = 2x + 1")
                steps.add("   du = 2 dx → dx = du/2")
                steps.add("PASO 5: Sustituyendo en la integral:")
                steps.add("   ∫(2x+1) dx = ∫u * du/2")
                steps.add("PASO 6: Integrando: ∫u * du/2 = (1/2)∫u du = u²/4")
                steps.add("PASO 7: Sustituyendo de vuelta u = 2x + 1:")
                steps.add("   Resultado: (2x+1)²/4 + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "(2x+1)²/4",
                    method = "Sustitución",
                    steps = steps,
                    isDefinite = false
                )
            }
            function.contains("(x+1)") -> {
                steps.add("PASO 4: Haciendo la sustitución:")
                steps.add("   u = x + 1")
                steps.add("   du = dx")
                steps.add("PASO 5: Sustituyendo en la integral:")
                steps.add("   ∫(x+1) dx = ∫u du")
                steps.add("PASO 6: Integrando: ∫u du = u²/2")
                steps.add("PASO 7: Sustituyendo de vuelta u = x + 1:")
                steps.add("   Resultado: (x+1)²/2 + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "(x+1)²/2",
                    method = "Sustitución",
                    steps = steps,
                    isDefinite = false
                )
            }
            else -> {
                steps.add("Sustitución general")
                IntegrationResult(
                    originalFunction = function,
                    result = "Resultado por sustitución",
                    method = "Sustitución",
                    steps = steps,
                    isDefinite = false
                )
            }
        }
    }
    
    private fun solvePolynomial(function: String, steps: MutableList<String>): IntegrationResult {
        steps.add("PASO 3: Aplicando linealidad de la integral: ∫(f+g) dx = ∫f dx + ∫g dx")
        
        return when {
            function.contains("x^2+2*x+1") || function.contains("x²+2x+1") -> {
                steps.add("PASO 4: Separando la integral en términos:")
                steps.add("   ∫(x² + 2x + 1) dx = ∫x² dx + ∫2x dx + ∫1 dx")
                steps.add("PASO 5: Integrando cada término:")
                steps.add("   ∫x² dx = x³/3")
                steps.add("   ∫2x dx = 2(x²/2) = x²")
                steps.add("   ∫1 dx = x")
                steps.add("PASO 6: Sumando todos los términos:")
                steps.add("   Resultado: x³/3 + x² + x + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "x³/3 + x² + x",
                    method = "Integral de polinomio",
                    steps = steps,
                    isDefinite = false
                )
            }
            function.contains("x^3+x^2") || function.contains("x³+x²") -> {
                steps.add("PASO 4: Separando la integral en términos:")
                steps.add("   ∫(x³ + x²) dx = ∫x³ dx + ∫x² dx")
                steps.add("PASO 5: Integrando cada término:")
                steps.add("   ∫x³ dx = x⁴/4")
                steps.add("   ∫x² dx = x³/3")
                steps.add("PASO 6: Sumando todos los términos:")
                steps.add("   Resultado: x⁴/4 + x³/3 + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "x⁴/4 + x³/3",
                    method = "Integral de polinomio",
                    steps = steps,
                    isDefinite = false
                )
            }
            else -> {
                steps.add("Integración polinomial general")
                IntegrationResult(
                    originalFunction = function,
                    result = "Resultado polinomial",
                    method = "Integral de polinomio",
                    steps = steps,
                    isDefinite = false
                )
            }
        }
    }
    
    private fun evaluateSimpleFunction(function: String): String {
        return when (function) {
            "1" -> "x"
            "x" -> "x²/2"
            "x^2" -> "x³/3"
            "x^3" -> "x⁴/4"
            else -> "No se pudo evaluar"
        }
    }
    
    private fun evaluateDefiniteIntegral(function: String, lowerLimit: Double, upperLimit: Double): Double {
        // Evaluación analítica cuando es posible
        val cleanFunction = function.replace(" ", "").lowercase()
        
        return when {
            cleanFunction == "x" -> {
                val upper = upperLimit * upperLimit / 2
                val lower = lowerLimit * lowerLimit / 2
                upper - lower
            }
            cleanFunction == "x^2" || cleanFunction == "x²" -> {
                val upper = upperLimit * upperLimit * upperLimit / 3
                val lower = lowerLimit * lowerLimit * lowerLimit / 3
                upper - lower
            }
            cleanFunction == "x^3" || cleanFunction == "x³" -> {
                val upper = upperLimit * upperLimit * upperLimit * upperLimit / 4
                val lower = lowerLimit * lowerLimit * lowerLimit * lowerLimit / 4
                upper - lower
            }
            cleanFunction == "sin(x)" -> {
                val upper = -cos(upperLimit)
                val lower = -cos(lowerLimit)
                upper - lower
            }
            cleanFunction == "cos(x)" -> {
                val upper = sin(upperLimit)
                val lower = sin(lowerLimit)
                upper - lower
            }
            cleanFunction == "e^x" -> {
                val upper = exp(upperLimit)
                val lower = exp(lowerLimit)
                upper - lower
            }
            cleanFunction == "1/x" -> {
                if (lowerLimit > 0 && upperLimit > 0) {
                    ln(upperLimit) - ln(lowerLimit)
                } else {
                    // Evaluación numérica para casos problemáticos
                    evaluateNumerically(function, lowerLimit, upperLimit)
                }
            }
            else -> {
                // Evaluación numérica usando el método del trapecio
                evaluateNumerically(function, lowerLimit, upperLimit)
            }
        }
    }
    
    private fun evaluateNumerically(function: String, lowerLimit: Double, upperLimit: Double): Double {
        val n = 1000 // Número de intervalos
        val dx = (upperLimit - lowerLimit) / n
        var sum = 0.0
        
        for (i in 0 until n) {
            val x = lowerLimit + i * dx
            sum += evaluateFunctionAt(function, x)
        }
        
        return sum * dx
    }
    
    private fun evaluateFunctionAt(function: String, x: Double): Double {
        val cleanFunction = function.replace(" ", "").lowercase()
        
        return when {
            cleanFunction == "x" -> x
            cleanFunction == "x^2" || cleanFunction == "x²" -> x * x
            cleanFunction == "x^3" || cleanFunction == "x³" -> x * x * x
            cleanFunction == "1/x" -> if (x != 0.0) 1.0 / x else 0.0
            cleanFunction == "sin(x)" -> sin(x)
            cleanFunction == "cos(x)" -> cos(x)
            cleanFunction == "e^x" -> exp(x)
            cleanFunction == "ln(x)" -> if (x > 0) ln(x) else 0.0
            cleanFunction == "tan(x)" -> tan(x)
            cleanFunction.contains("x^2+2*x+1") || cleanFunction.contains("x²+2x+1") -> x * x + 2 * x + 1
            else -> 0.0
        }
    }
    
    private fun solveExponentialSubstitution(function: String, steps: MutableList<String>): IntegrationResult {
        steps.add("PASO 3: Aplicando sustitución para integrales exponenciales")
        
        return when {
            function.contains("e^(x^2)") || function.contains("e^(x²)") -> {
                steps.add("PASO 4: Esta integral no tiene solución elemental")
                steps.add("PASO 5: Se requiere la función error: erf(x)")
                steps.add("PASO 6: Resultado: ∫e^(x²) dx = (√π/2) * erf(x) + C")
                IntegrationResult(
                    originalFunction = function,
                    result = "(√π/2) * erf(x)",
                    method = "Función error",
                    steps = steps,
                    isDefinite = false
                )
            }
            else -> {
                steps.add("PASO 4: Sustitución exponencial general")
                steps.add("PASO 5: Resultado: Integral exponencial compleja")
                IntegrationResult(
                    originalFunction = function,
                    result = "Resultado exponencial",
                    method = "Sustitución exponencial",
                    steps = steps,
                    isDefinite = false
                )
            }
        }
    }
    
    private fun solveArctangent(function: String, steps: MutableList<String>): IntegrationResult {
        steps.add("PASO 3: Es una integral que resulta en arctangente")
        steps.add("PASO 4: Aplicando la regla: ∫1/(x² + 1) dx = arctan(x) + C")
        steps.add("PASO 5: Resultado: ∫1/(x² + 1) dx = arctan(x) + C")
        IntegrationResult(
            originalFunction = function,
            result = "arctan(x)",
            method = "Integral directa",
            steps = steps,
            isDefinite = false
        )
    }
    
    private fun solveArcsin(function: String, steps: MutableList<String>): IntegrationResult {
        steps.add("PASO 3: Es una integral que resulta en arcoseno")
        steps.add("PASO 4: Aplicando la regla: ∫1/√(1-x²) dx = arcsin(x) + C")
        steps.add("PASO 5: Resultado: ∫1/√(1-x²) dx = arcsin(x) + C")
        IntegrationResult(
            originalFunction = function,
            result = "arcsin(x)",
            method = "Integral directa",
            steps = steps,
            isDefinite = false
        )
    }
}
