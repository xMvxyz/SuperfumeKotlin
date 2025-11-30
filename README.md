# SuperfumeKotlin

## Descripción

SuperfumeKotlin es una aplicación móvil Android desarrollada en Kotlin que proporciona una plataforma completa para la venta y gestión de perfumes. La aplicación implementa arquitectura moderna con Jetpack Compose, ofreciendo una experiencia de usuario fluida y profesional.

## Características Principales

### Gestión de Productos
- Catálogo completo de perfumes con información detallada
- Búsqueda y filtrado por categoría, marca y género
- Visualización de detalles incluyendo precio, descripción, tamaño y disponibilidad
- Gestión de inventario con control de stock
- Soporte para imágenes de productos

### Sistema de Autenticación
- Registro de nuevos usuarios
- Inicio de sesión seguro
- Gestión de perfiles de usuario
- Actualización de información personal

### Carrito de Compras
- Agregar productos al carrito con cantidades personalizables
- Actualización dinámica de cantidades
- Cálculo automático de totales
- Eliminación de productos del carrito
- Persistencia del carrito por usuario

### Administración
- Interfaz para agregar nuevos perfumes
- Actualización de información de productos
- Gestión de stock y disponibilidad
- Captura de imágenes mediante cámara o galería

## Tecnologías Utilizadas

### Framework y Lenguaje
- **Kotlin 1.9.22**: Lenguaje de programación principal
- **Android SDK**: Nivel mínimo 24, objetivo 34
- **Jetpack Compose**: Framework moderno para UI declarativa

### Arquitectura y Componentes
- **MVVM Pattern**: Separación clara entre UI, lógica y datos
- **Room Database 2.6.1**: Persistencia local de datos
- **Hilt 2.48**: Inyección de dependencias
- **Kotlin Coroutines**: Manejo asíncrono de operaciones
- **StateFlow**: Gestión reactiva de estados

### UI y Navegación
- **Material Design 3**: Diseño moderno y consistente
- **Navigation Compose 2.7.6**: Navegación entre pantallas
- **Coil 2.5.0**: Carga eficiente de imágenes
- **CameraX 1.3.1**: Integración con cámara del dispositivo

### Testing
- **JUnit 4.13.2**: Testing unitario
- **Espresso 3.5.1**: Testing de UI
- **Android Test Extensions**: Testing de componentes Android

## Estructura del Proyecto

```
app/src/main/java/com/SuperfumeKotlin/
├── data/
│   ├── dao/                    # Data Access Objects
│   │   ├── CartDao.kt         # Operaciones del carrito
│   │   ├── PerfumeDao.kt      # Operaciones de perfumes
│   │   └── UserDao.kt         # Operaciones de usuarios
│   ├── database/
│   │   └── SuperfumeDatabase.kt  # Configuración de Room
│   ├── model/                 # Entidades de datos
│   │   ├── CartItem.kt        # Modelo de elemento del carrito
│   │   ├── Perfume.kt         # Modelo de perfume
│   │   └── User.kt            # Modelo de usuario
│   ├── repository/
│   │   └── SuperfumeRepository.kt  # Capa de repositorio
│   └── DataInitializer.kt     # Datos iniciales
├── di/
│   └── DatabaseModule.kt      # Módulos de Hilt
├── ui/
│   ├── components/            # Componentes reutilizables
│   │   ├── AdvancedImagePicker.kt
│   │   ├── CameraPreview.kt
│   │   ├── ImagePicker.kt
│   │   └── ValidatedTextField.kt
│   ├── navigation/
│   │   └── SuperfumeNavigation.kt  # Configuración de rutas
│   ├── screens/               # Pantallas de la aplicación
│   │   ├── admin/
│   │   │   └── AddPerfumeScreen.kt
│   │   ├── auth/
│   │   │   ├── LoginScreen.kt
│   │   │   └── RegisterScreen.kt
│   │   ├── cart/
│   │   │   └── CartScreen.kt
│   │   ├── home/
│   │   │   └── HomeScreen.kt
│   │   ├── perfume/
│   │   │   └── PerfumeDetailScreen.kt
│   │   └── profile/
│   │       └── ProfileScreen.kt
│   ├── theme/                 # Configuración de tema
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodel/            # ViewModels
│       ├── AuthViewModel.kt
│       ├── CartViewModel.kt
│       └── PerfumeViewModel.kt
├── util/                      # Utilidades
│   ├── Constantes.kt
│   ├── FormValidator.kt
│   └── TextResources.kt
├── MainActivity.kt            # Actividad principal
└── SuperfumeApplication.kt   # Clase Application

```

## Base de Datos

### Tablas

#### Perfumes
```kotlin
- id: Long (PK, autoincrement)
- name: String
- brand: String
- price: Double
- description: String
- imageUri: String?
- category: String
- size: String
- gender: String
- isAvailable: Boolean
- stock: Int
```

#### Usuarios
```kotlin
- id: Long (PK, autoincrement)
- email: String
- password: String
- firstName: String
- lastName: String
- phone: String?
- address: String?
- profileImageUri: String?
```

#### Elementos del Carrito
```kotlin
- id: Long (PK, autoincrement)
- userId: Long (FK)
- perfumeId: Long (FK)
- quantity: Int
- addedAt: Long
```

## Instalación y Configuración

### Requisitos Previos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 11 o superior
- Android SDK 34
- Dispositivo o emulador con Android 7.0 (API 24) o superior

### Pasos de Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/xMvxyz/SuperfumeKotlin.git
cd SuperfumeKotlin
```

2. Abrir el proyecto en Android Studio

3. Sincronizar dependencias de Gradle:
```bash
./gradlew build
```

4. Ejecutar la aplicación:
- Conectar un dispositivo Android o iniciar un emulador
- Presionar Run en Android Studio o ejecutar:
```bash
./gradlew installDebug
```

## Configuración del Proyecto

### Gradle

El proyecto utiliza Gradle Version Catalogs para gestión de dependencias. Las versiones se encuentran en `gradle/libs.versions.toml`.

### Permisos Requeridos

La aplicación solicita los siguientes permisos:
- `CAMERA`: Para capturar imágenes de productos
- `READ_EXTERNAL_STORAGE`: Para seleccionar imágenes de la galería
- `WRITE_EXTERNAL_STORAGE`: Para guardar imágenes
- `READ_MEDIA_IMAGES`: Para acceso a imágenes en Android 13+

## Uso de la Aplicación

### Flujo de Usuario

1. **Registro/Login**: Los usuarios deben crear una cuenta o iniciar sesión
2. **Exploración**: Navegar por el catálogo de perfumes
3. **Búsqueda**: Utilizar filtros para encontrar productos específicos
4. **Detalles**: Ver información completa del producto
5. **Carrito**: Agregar productos con cantidades deseadas
6. **Checkout**: Revisar y finalizar la compra

### Funcionalidades de Administración

Los administradores pueden:
- Agregar nuevos perfumes con imágenes
- Actualizar información de productos existentes
- Gestionar inventario y stock
- Ver estadísticas de ventas

## Arquitectura

### Patrón MVVM

```
View (Composables)
    ↓
ViewModel (StateFlow)
    ↓
Repository (Abstracción)
    ↓
DAO (Room)
    ↓
Database (SQLite)
```

### Inyección de Dependencias

Hilt proporciona:
- Singleton de Database
- Singleton de Repository
- Scoped ViewModels
- DAOs configurados automáticamente

### Gestión de Estado

- StateFlow para estados reactivos
- Coroutines para operaciones asíncronas
- Flow para streams de datos continuos

## Testing

### Tests Unitarios
```bash
./gradlew test
```

### Tests de Instrumentación
```bash
./gradlew connectedAndroidTest
```

## Construcción de Release

```bash
./gradlew assembleRelease
```

El APK se generará en: `app/build/outputs/apk/release/`

## Versionado

- **Version Code**: 1
- **Version Name**: 1.0

## Licencia

Este proyecto es de código propietario.

## Autores

- Desarrollador Principal: xMvxyz
- Repositorio: https://github.com/xMvxyz/SuperfumeKotlin

## Contacto

Para reportar problemas o sugerencias, crear un issue en el repositorio de GitHub.

## Próximas Características

- Integración con pasarelas de pago
- Sistema de reseñas y calificaciones
- Notificaciones push
- Wishlist de productos
- Historial de pedidos
- Backend REST API
- Sincronización en la nube
- Modo offline mejorado

## Notas de Desarrollo

### Compilación Exitosa
El proyecto está configurado con:
- Kotlin Compiler Extension: 1.5.8
- KSP para procesamiento de anotaciones de Room
- Build Config habilitado
- ProGuard configurado para release

### Consideraciones de Rendimiento
- Lazy loading de imágenes con Coil
- Paginación en listas largas
- Índices optimizados en Room
- Coroutines para operaciones pesadas

---

Última actualización: Noviembre 2025
