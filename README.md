# Integrales App 📱🧮

Una aplicación Android para resolver integrales matemáticas con gráficas y reconocimiento de imágenes.

## 🚀 Características

### ✨ Funcionalidades Principales
- **Resolución de Integrales**: Soporta múltiples métodos de integración
- **Gráficas Interactivas**: Visualización de funciones y sus integrales
- **Reconocimiento de Imágenes**: Captura y procesa integrales escritas a mano
- **Interfaz Moderna**: Diseño Material Design intuitivo y funcional

### 📊 Métodos de Integración Soportados
1. **Integrales Directas** - Reglas básicas de integración
2. **Sustitución** - Método de sustitución u = g(x)
3. **Por Partes** - Integración por partes ∫u dv = uv - ∫v du
4. **Sustitución Trigonométrica** - Para integrales trigonométricas complejas
5. **Integrales Definidas** - Con límites de integración y Teorema Fundamental
6. **Funciones Especiales** - arcsin, arctan, erf, etc.
7. **Identidades Trigonométricas** - sin²(x), cos²(x), etc.

### 🎯 Funciones Soportadas
- **Polinomios**: x, x², x³, x⁴, etc.
- **Funciones Trigonométricas**: sin(x), cos(x), tan(x), sec(x)
- **Funciones Exponenciales**: e^x, ln(x)
- **Funciones Racionales**: 1/x, 1/(x²+1)
- **Funciones Inversas**: arcsin(x), arctan(x)
- **Funciones Especiales**: erf(x) para e^(x²)
- **Expresiones Compuestas**: x² + 2x + 1, etc.
- **Identidades Trigonométricas**: sin²(x), cos²(x), etc.

## 📱 Capturas de Pantalla

### Pantalla Principal
- Entrada de función matemática
- Selección de tipo de integral (indefinida/definida)
- Botones para resolver y capturar imagen

### Resultados
- Solución paso a paso
- Método utilizado
- Valor numérico (para integrales definidas)

### Gráficas
- Visualización de función original
- Visualización de la integral
- Controles de zoom y navegación

## 🛠️ Instalación

### Requisitos Previos
- Android Studio Arctic Fox o superior
- Android SDK API 21+ (Android 5.0)
- Gradle 7.0+

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/integrales-app.git
   cd integrales-app
   ```

2. **Abrir en Android Studio**
   - Abrir Android Studio
   - Seleccionar "Open an existing project"
   - Navegar a la carpeta del proyecto y seleccionarla

3. **Sincronizar dependencias**
   - Esperar a que Gradle sincronice las dependencias
   - Si hay errores, hacer clic en "Sync Now"

4. **Configurar dispositivo**
   - Conectar dispositivo Android o crear un emulador
   - Habilitar "Opciones de desarrollador" y "Depuración USB"

5. **Compilar y ejecutar**
   - Presionar F5 o hacer clic en "Run"
   - Seleccionar el dispositivo objetivo
   - La app se instalará automáticamente

## 📋 Uso de la Aplicación

### Resolver una Integral

1. **Ingresar función**
   - Escribir la función en el campo de texto
   - Ejemplos: `x^2`, `sin(x)`, `x^2 + 2*x + 1`

2. **Seleccionar tipo**
   - **Indefinida**: Sin límites de integración
   - **Definida**: Con límites superior e inferior

3. **Resolver**
   - Hacer clic en "Resolver Integral"
   - Ver el resultado y pasos de resolución

### Usar Reconocimiento de Imágenes

1. **Capturar imagen**
   - Hacer clic en "Tomar Foto"
   - Seleccionar "Cámara" o "Galería"
   - Capturar o seleccionar imagen con integral

2. **Procesar**
   - La app reconocerá automáticamente la función
   - Se llenará el campo de entrada
   - Proceder a resolver normalmente

### Ver Gráficas

1. **Después de resolver**
   - Hacer clic en "Ver Gráfica"
   - Se abrirá la pantalla de gráficas

2. **Controles disponibles**
   - **Zoom +**: Acercar la gráfica
   - **Zoom -**: Alejar la gráfica
   - **Función Original**: Mostrar solo la función original
   - **Integral**: Mostrar función original e integral

## 🔧 Configuración Avanzada

### Permisos Requeridos
- **Cámara**: Para capturar imágenes de integrales
- **Almacenamiento**: Para guardar fotos capturadas
- **Internet**: Para funcionalidades futuras

### Personalización
- Modificar colores en `app/src/main/res/values/colors.xml`
- Cambiar textos en `app/src/main/res/values/strings.xml`
- Ajustar temas en `app/src/main/res/values/themes.xml`

## 📚 Ejemplos de Uso

### Integrales Básicas
```
Entrada: x^2
Resultado: x³/3 + C
Método: Integral directa
```

### Integrales Trigonométricas
```
Entrada: sin(x)
Resultado: -cos(x) + C
Método: Integral trigonométrica
```

### Integrales por Partes
```
Entrada: x*ln(x)
Resultado: x²*ln(x)/2 - x²/4 + C
Método: Por partes
Pasos: Identificación de u y dv, aplicación de fórmula
```

### Integrales Trigonométricas
```
Entrada: sin²(x)
Resultado: x/2 - sin(2x)/4 + C
Método: Sustitución trigonométrica
Pasos: Uso de identidades trigonométricas
```

### Integrales Definidas
```
Entrada: x^2, límites [0, 1]
Resultado: x³/3 evaluado de 0 a 1 = 1/3
Método: Teorema Fundamental del Cálculo
Pasos: Evaluación analítica en límites
```

### Funciones Especiales
```
Entrada: 1/(x²+1)
Resultado: arctan(x) + C
Método: Integral directa
```

## 🐛 Solución de Problemas

### Errores Comunes

1. **"Función inválida"**
   - Verificar sintaxis de la función
   - Usar `^` para exponentes
   - Usar `*` para multiplicación

2. **"Error en el cálculo"**
   - Verificar que la función esté bien formada
   - Revisar límites de integración

3. **Cámara no funciona**
   - Verificar permisos de cámara
   - Reiniciar la aplicación

4. **Gráfica no se muestra**
   - Verificar que la función sea válida
   - Ajustar rango de visualización

### Logs de Depuración
```bash
adb logcat | grep "IntegralesApp"
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 👨‍💻 Autor

**Tu Nombre**
- GitHub: [@tu-usuario](https://github.com/tu-usuario)
- Email: tu-email@ejemplo.com

## 🙏 Agradecimientos

- **MPAndroidChart**: Para las gráficas interactivas
- **ML Kit**: Para el reconocimiento de texto
- **CameraX**: Para la funcionalidad de cámara
- **Material Design**: Para el diseño de la interfaz

## 📞 Soporte

Si tienes problemas o preguntas:
- Abrir un [Issue](https://github.com/tu-usuario/integrales-app/issues)
- Contactar por email: tu-email@ejemplo.com

---

⭐ Si te gusta este proyecto, ¡dale una estrella en GitHub!
