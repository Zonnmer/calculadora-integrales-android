package com.integrales.app

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.integrales.app.databinding.ActivityGraphBinding
import kotlin.math.*

class GraphActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityGraphBinding
    private var originalFunction: String = ""
    private var integralResult: String = ""
    private var currentXMin = -5.0
    private var currentXMax = 5.0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupChart()
        loadData()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupChart() {
        binding.lineChart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(true)
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return String.format("%.1f", value)
                    }
                }
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return String.format("%.1f", value)
                    }
                }
            }
            
            axisRight.isEnabled = false
        }
    }
    
    private fun loadData() {
        originalFunction = intent.getStringExtra("function") ?: "x^2"
        integralResult = intent.getStringExtra("result") ?: ""
        
        val infoText = StringBuilder()
        infoText.append("Función original: $originalFunction\n")
        if (integralResult.isNotEmpty()) {
            infoText.append("Integral: $integralResult")
        }
        binding.functionInfoText.text = infoText.toString()
        
        showOriginalFunction()
    }
    
    private fun setupListeners() {
        binding.zoomInButton.setOnClickListener {
            zoomIn()
        }
        
        binding.zoomOutButton.setOnClickListener {
            zoomOut()
        }
        
        binding.showOriginalButton.setOnClickListener {
            showOriginalFunction()
        }
        
        binding.showIntegralButton.setOnClickListener {
            showIntegralFunction()
        }
    }
    
    private fun showOriginalFunction() {
        val entries = generateFunctionPoints(originalFunction, currentXMin, currentXMax)
        val dataSet = LineDataSet(entries, "Función Original").apply {
            color = Color.BLUE
            setCircleColor(Color.BLUE)
            lineWidth = 2f
            circleRadius = 3f
            setDrawCircles(false)
            setDrawValues(false)
        }
        
        binding.lineChart.data = LineData(dataSet)
        binding.lineChart.invalidate()
    }
    
    private fun showIntegralFunction() {
        if (integralResult.isEmpty()) {
            showOriginalFunction()
            return
        }
        
        val originalEntries = generateFunctionPoints(originalFunction, currentXMin, currentXMax)
        val integralEntries = generateFunctionPoints(integralResult, currentXMin, currentXMax)
        
        val originalDataSet = LineDataSet(originalEntries, "Función Original").apply {
            color = Color.BLUE
            setCircleColor(Color.BLUE)
            lineWidth = 2f
            circleRadius = 3f
            setDrawCircles(false)
            setDrawValues(false)
        }
        
        val integralDataSet = LineDataSet(integralEntries, "Integral").apply {
            color = Color.RED
            setCircleColor(Color.RED)
            lineWidth = 2f
            circleRadius = 3f
            setDrawCircles(false)
            setDrawValues(false)
        }
        
        binding.lineChart.data = LineData(originalDataSet, integralDataSet)
        binding.lineChart.invalidate()
    }
    
    private fun generateFunctionPoints(function: String, xMin: Double, xMax: Double): List<Entry> {
        val entries = mutableListOf<Entry>()
        val step = (xMax - xMin) / 200 // 200 puntos para una gráfica suave
        
        var x = xMin
        while (x <= xMax) {
            try {
                val y = evaluateFunction(function, x)
                if (y.isFinite() && y.isInfinite().not()) {
                    entries.add(Entry(x.toFloat(), y.toFloat()))
                }
            } catch (e: Exception) {
                // Ignorar puntos donde la función no está definida
            }
            x += step
        }
        
        return entries
    }
    
    private fun evaluateFunction(function: String, x: Double): Double {
        val cleanFunction = function.replace(" ", "").lowercase()
        
        return when {
            cleanFunction == "x" -> x
            cleanFunction == "x^2" || cleanFunction == "x²" -> x * x
            cleanFunction == "x^3" || cleanFunction == "x³" -> x * x * x
            cleanFunction == "x^4" || cleanFunction == "x⁴" -> x * x * x * x
            cleanFunction == "1/x" -> if (x != 0.0) 1.0 / x else Double.NaN
            cleanFunction == "sin(x)" -> sin(x)
            cleanFunction == "cos(x)" -> cos(x)
            cleanFunction == "e^x" -> exp(x)
            cleanFunction == "ln(x)" -> if (x > 0) ln(x) else Double.NaN
            cleanFunction == "x^2/2" || cleanFunction == "x²/2" -> x * x / 2
            cleanFunction == "x^3/3" || cleanFunction == "x³/3" -> x * x * x / 3
            cleanFunction == "x^4/4" || cleanFunction == "x⁴/4" -> x * x * x * x / 4
            cleanFunction == "-cos(x)" -> -cos(x)
            cleanFunction == "x^2+2*x+1" || cleanFunction == "x²+2x+1" -> x * x + 2 * x + 1
            cleanFunction == "x^3/3+x^2+x" || cleanFunction == "x³/3+x²+x" -> x * x * x / 3 + x * x + x
            cleanFunction == "x^4/4+x^3/3" || cleanFunction == "x⁴/4+x³/3" -> x * x * x * x / 4 + x * x * x / 3
            cleanFunction == "(x+1)^2/2" -> (x + 1) * (x + 1) / 2
            cleanFunction == "(2x+1)^2/4" -> (2 * x + 1) * (2 * x + 1) / 4
            cleanFunction == "x^2*ln(x)/2-x^2/4" || cleanFunction == "x²*ln(x)/2-x²/4" -> 
                if (x > 0) x * x * ln(x) / 2 - x * x / 4 else Double.NaN
            cleanFunction == "x*e^x-e^x" -> x * exp(x) - exp(x)
            cleanFunction == "x/2-sin(2x)/4" -> x / 2 - sin(2 * x) / 4
            cleanFunction == "x/2+sin(2x)/4" -> x / 2 + sin(2 * x) / 4
            else -> {
                // Intentar evaluar expresiones más complejas
                try {
                    evaluateComplexFunction(cleanFunction, x)
                } catch (e: Exception) {
                    Double.NaN
                }
            }
        }
    }
    
    private fun evaluateComplexFunction(function: String, x: Double): Double {
        // Evaluación simple de expresiones polinomiales
        var result = 0.0
        var currentTerm = ""
        var sign = 1
        
        for (char in function) {
            when (char) {
                '+' -> {
                    if (currentTerm.isNotEmpty()) {
                        result += sign * evaluateTerm(currentTerm, x)
                        currentTerm = ""
                    }
                    sign = 1
                }
                '-' -> {
                    if (currentTerm.isNotEmpty()) {
                        result += sign * evaluateTerm(currentTerm, x)
                        currentTerm = ""
                    }
                    sign = -1
                }
                else -> currentTerm += char
            }
        }
        
        if (currentTerm.isNotEmpty()) {
            result += sign * evaluateTerm(currentTerm, x)
        }
        
        return result
    }
    
    private fun evaluateTerm(term: String, x: Double): Double {
        val cleanTerm = term.trim()
        
        return when {
            cleanTerm == "x" -> x
            cleanTerm == "x^2" || cleanTerm == "x²" -> x * x
            cleanTerm == "x^3" || cleanTerm == "x³" -> x * x * x
            cleanTerm == "1" -> 1.0
            cleanTerm == "2" -> 2.0
            cleanTerm == "3" -> 3.0
            cleanTerm == "4" -> 4.0
            cleanTerm.matches(Regex("\\d+")) -> cleanTerm.toDouble()
            else -> 0.0
        }
    }
    
    private fun zoomIn() {
        val range = currentXMax - currentXMin
        val newRange = range * 0.8
        val center = (currentXMax + currentXMin) / 2
        currentXMin = center - newRange / 2
        currentXMax = center + newRange / 2
        
        if (binding.showIntegralButton.isSelected) {
            showIntegralFunction()
        } else {
            showOriginalFunction()
        }
    }
    
    private fun zoomOut() {
        val range = currentXMax - currentXMin
        val newRange = range * 1.2
        val center = (currentXMax + currentXMin) / 2
        currentXMin = center - newRange / 2
        currentXMax = center + newRange / 2
        
        if (binding.showIntegralButton.isSelected) {
            showIntegralFunction()
        } else {
            showOriginalFunction()
        }
    }
}

