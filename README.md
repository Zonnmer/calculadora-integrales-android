# Calculadora de Integrales Android

Una aplicación Android completa para resolver integrales matemáticas con pasos detallados.

## 🚀 **NUEVO BUILD EN GITHUB ACTIONS** 🚀

**Última actualización:** $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")

## Características

- ✅ **Integrales Indefinidas y Definidas**
- ✅ **Pasos detallados de resolución**
- ✅ **Múltiples métodos de integración**
- ✅ **Interfaz moderna y intuitiva**
- ✅ **Soporte para funciones trigonométricas**

## Métodos de Integración Soportados

- **Integrales Directas** (x^n, e^x, ln(x))
- **Sustitución** (u = g(x))
- **Por Partes** (∫u dv = uv - ∫v du)
- **Trigonométricas** (sin, cos, tan, sec)
- **Sustitución Trigonométrica** (identidades)
- **Definidas** (Teorema Fundamental)
- **Funciones Especiales** (arcsin, arctan, erf)

## Funciones Soportadas

### Básicas
- `x^n` (potencias)
- `e^x` (exponencial)
- `ln(x)` (logaritmo natural)
- `1/x` (inversa)

### Trigonométricas
- `sin(x)`, `cos(x)`, `tan(x)`
- `sec(x)`, `csc(x)`, `cot(x)`
- `sin^2(x)`, `cos^2(x)`, `tan^2(x)`

### Especiales
- `1/(1+x^2)` → `arctan(x)`
- `1/sqrt(1-x^2)` → `arcsin(x)`
- `e^(-x^2)` → función error

## Ejemplos de Uso

### Integral Indefinida
```
Entrada: ∫x^2 dx
Resultado: x^3/3 + C
```

### Integral Definida
```
Entrada: ∫[0,1] x^2 dx
Resultado: 1/3
```

### Por Partes
```
Entrada: ∫x*ln(x) dx
Paso 1: u = ln(x), dv = x dx
Paso 2: du = 1/x dx, v = x^2/2
Paso 3: ∫x*ln(x) dx = x^2*ln(x)/2 - ∫x^2/2 * 1/x dx
Paso 4: ∫x*ln(x) dx = x^2*ln(x)/2 - x^2/4 + C
```

## Instalación

1. Descarga el APK desde la sección "Releases"
2. Habilita "Instalar aplicaciones de orígenes desconocidos"
3. Instala la aplicación

## Tecnologías

- **Kotlin** - Lenguaje principal
- **AndroidX** - Componentes modernos
- **Material Design** - UI/UX
- **Apache Commons Math** - Procesamiento matemático
- **Coroutines** - Programación asíncrona

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/integrales/app/
│   │   ├── MainActivity.kt
│   │   └── math/
│   │       └── IntegralSolver.kt
│   ├── res/
│   │   ├── layout/
│   │   ├── values/
│   │   └── drawable/
│   └── AndroidManifest.xml
└── build.gradle
```

## Build Status

![GitHub Actions](https://github.com/Zonnmer/calculadora-integrales-android/workflows/Build%20Android%20APK/badge.svg)

**¡APK automático generado en cada commit!**
