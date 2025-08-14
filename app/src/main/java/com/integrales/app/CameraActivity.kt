package com.integrales.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.integrales.app.databinding.ActivityCameraBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var photoFile: File? = null
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(this, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        
        cameraExecutor = Executors.newSingleThreadExecutor()
        
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.captureButton.setOnClickListener {
            takePhoto()
        }
        
        binding.cancelButton.setOnClickListener {
            finish()
        }
        
        binding.usePhotoButton.setOnClickListener {
            processPhoto()
        }
    }
    
    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        baseContext, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
    
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }
            
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            
            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, TextAnalyzer())
                }
            
            try {
                cameraProvider.unbindAll()
                
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture,
                    imageAnalyzer
                )
                
            } catch (exc: Exception) {
                Toast.makeText(this, "Error al iniciar la cámara", Toast.LENGTH_SHORT).show()
            }
            
        }, ContextCompat.getMainExecutor(this))
    }
    
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        
        photoFile = File(
            outputDirectory,
            SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )
        
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile!!).build()
        
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    binding.usePhotoButton.visibility = android.view.View.VISIBLE
                    Toast.makeText(baseContext, "Foto capturada", Toast.LENGTH_SHORT).show()
                }
                
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(baseContext, "Error al capturar foto", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
    
    private fun processPhoto() {
        photoFile?.let { file ->
            val image = InputImage.fromFilePath(this, Uri.fromFile(file))
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val detectedText = visionText.text
                    if (detectedText.isNotEmpty()) {
                        // Intentar extraer una función matemática del texto
                        val function = extractMathematicalFunction(detectedText)
                        if (function.isNotEmpty()) {
                            val resultIntent = Intent().apply {
                                putExtra("detected_function", function)
                            }
                            setResult(RESULT_OK, resultIntent)
                            finish()
                        } else {
                            Toast.makeText(this, "No se detectó una función matemática", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, "No se detectó texto", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al procesar imagen: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
    
    private fun extractMathematicalFunction(text: String): String {
        // Algoritmo simple para extraer funciones matemáticas del texto reconocido
        val lines = text.split("\n")
        
        for (line in lines) {
            val cleanLine = line.trim()
            
            // Buscar patrones comunes de funciones matemáticas
            if (cleanLine.contains("∫") || cleanLine.contains("integral")) {
                // Extraer la función después del símbolo de integral
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
    
    private val outputDirectory: File by lazy {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
    
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
    
    private inner class TextAnalyzer : ImageAnalysis.Analyzer {
        @androidx.camera.core.ExperimentalGetImage
        override fun analyze(imageProxy: ImageProxy) {
            // Análisis en tiempo real del texto (opcional)
            imageProxy.close()
        }
    }
}

