package com.integrales.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.integrales.app.databinding.ActivityMainBinding
import com.integrales.app.math.IntegralSolver
import com.integrales.app.math.IntegrationResult
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val integralSolver = IntegralSolver()
    
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { processImageFromGallery(it) }
    }
    
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // La foto se guardó en tempImageUri
            processImageFromCamera()
        }
    }
    
    private var tempImageUri: Uri? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        
        // Configurar radio buttons para mostrar/ocultar límites
        binding.definiteRadio.setOnCheckedChangeListener { _, isChecked ->
            binding.limitsLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }
    
    private fun setupListeners() {
        binding.solveButton.setOnClickListener {
            solveIntegral()
        }
        
        binding.cameraButton.setOnClickListener {
            showImageSourceDialog()
        }
        
        binding.graphButton.setOnClickListener {
            showGraph()
        }
    }
    
    private fun solveIntegral() {
        val function = binding.functionInput.text.toString().trim()
        if (function.isEmpty()) {
            binding.functionInputLayout.error = "Ingresa una función"
            return
        }
        
        binding.functionInputLayout.error = null
        
        val isDefinite = binding.definiteRadio.isChecked
        val lowerLimit = if (isDefinite) binding.lowerLimitInput.text.toString().toDoubleOrNull() else null
        val upperLimit = if (isDefinite) binding.upperLimitInput.text.toString().toDoubleOrNull() else null
        
        if (isDefinite && (lowerLimit == null || upperLimit == null)) {
            Toast.makeText(this, "Ingresa límites válidos", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Mostrar loading
        binding.solveButton.isEnabled = false
        binding.solveButton.text = "Calculando..."
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    if (isDefinite) {
                        integralSolver.solveDefiniteIntegral(function, lowerLimit!!, upperLimit!!)
                    } else {
                        integralSolver.solveIndefiniteIntegral(function)
                    }
                }
                
                displayResult(result)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                binding.solveButton.isEnabled = true
                binding.solveButton.text = getString(R.string.btn_solve)
            }
        }
    }
    
    private fun displayResult(result: IntegrationResult) {
        binding.resultsCard.visibility = View.VISIBLE
        
        val resultText = StringBuilder()
        
        if (result.isDefinite) {
            resultText.append("Integral Definida:\n")
            resultText.append("∫(${result.originalFunction}) dx = ${result.result}\n\n")
            resultText.append("Valor numérico: ${result.numericValue}\n\n")
        } else {
            resultText.append("Integral Indefinida:\n")
            resultText.append("∫(${result.originalFunction}) dx = ${result.result} + C\n\n")
        }
        
        if (result.method.isNotEmpty()) {
            resultText.append("Método usado: ${result.method}\n\n")
        }
        
        if (result.steps.isNotEmpty()) {
            resultText.append("Pasos de resolución:\n")
            result.steps.forEachIndexed { index, step ->
                resultText.append("${index + 1}. $step\n")
            }
        }
        
        binding.resultText.text = resultText.toString()
    }
    
    private fun showImageSourceDialog() {
        val options = arrayOf("Cámara", "Galería")
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Seleccionar imagen")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkCameraPermission()
                    1 -> openGallery()
                }
            }
            .show()
    }
    
    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    
    private fun openCamera() {
        tempImageUri = createImageUri()
        cameraLauncher.launch(tempImageUri)
    }
    
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }
    
    private fun createImageUri(): Uri {
        val timeStamp = System.currentTimeMillis()
        val imageFileName = "INTEGRAL_${timeStamp}"
        val contentValues = android.content.ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }
    
    private fun processImageFromCamera() {
        tempImageUri?.let { uri ->
            processImage(uri)
        }
    }
    
    private fun processImageFromGallery(uri: Uri) {
        processImage(uri)
    }
    
    private fun processImage(uri: Uri) {
        Toast.makeText(this, "Procesando imagen...", Toast.LENGTH_SHORT).show()
        
        try {
            val image = InputImage.fromFilePath(this, uri)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val detectedText = visionText.text
                    if (detectedText.isNotEmpty()) {
                        val function = extractMathematicalFunction(detectedText)
                        if (function.isNotEmpty()) {
                            binding.functionInput.setText(function)
                            Toast.makeText(this, "Función detectada: $function", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "No se detectó una función matemática válida", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, "No se detectó texto en la imagen", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al procesar imagen: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun extractMathematicalFunction(text: String): String {
        val lines = text.split("\n")
        
        for (line in lines) {
            val cleanLine = line.trim()
            
            // Buscar patrones comunes de funciones matemáticas
            if (cleanLine.contains("∫") || cleanLine.contains("integral")) {
                val parts = cleanLine.split("∫", "integral")
                if (parts.size > 1) {
                    val function = parts[1].trim()
                    return cleanMathematicalExpression(function)
                }
            }
            
            // Buscar expresiones que contengan x y operadores matemáticos
            if (cleanLine.contains("x") && (cleanLine.contains("+") || cleanLine.contains("-") || 
                cleanLine.contains("*") || cleanLine.contains("^") || cleanLine.contains("²") || 
                cleanLine.contains("³"))) {
                return cleanMathematicalExpression(cleanLine)
            }
            
            // Buscar funciones trigonométricas
            if (cleanLine.contains("sin") || cleanLine.contains("cos") || 
                cleanLine.contains("tan") || cleanLine.contains("e^")) {
                return cleanMathematicalExpression(cleanLine)
            }
        }
        
        return ""
    }
    
    private fun cleanMathematicalExpression(expression: String): String {
        var cleaned = expression
            .replace(" ", "")
            .replace("dx", "")
            .replace("=", "")
            .replace("+", "+")
            .replace("-", "-")
            .replace("×", "*")
            .replace("·", "*")
            .replace("²", "^2")
            .replace("³", "^3")
            .replace("⁴", "^4")
        
        // Limpiar caracteres no válidos
        cleaned = cleaned.replace(Regex("[^x0-9+\\-*^()/\\s\\.]"), "")
        
        return cleaned
    }
    
    private fun showGraph() {
        val function = binding.functionInput.text.toString().trim()
        if (function.isNotEmpty()) {
            val intent = Intent(this, GraphActivity::class.java).apply {
                putExtra("function", function)
                putExtra("result", binding.resultText.text.toString())
            }
            startActivity(intent)
        }
    }
}
