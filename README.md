# Proyecto Superfume

Este proyecto es una tienda de perfumes donde puedes comprar perfumes, gestionar tu carrito y realizar pedidos.

## Rutas principales del proyecto

| Componente | Ruta principal |
|------------|---------------|
| MainActivity | app/src/main/java/com/SuperfumeKotlin/MainActivity.kt |
| Login | app/src/main/java/com/SuperfumeKotlin/ui/screens/auth/LoginScreen.kt |
| Registro | app/src/main/java/com/SuperfumeKotlin/ui/screens/auth/RegisterScreen.kt |
| Home | app/src/main/java/com/SuperfumeKotlin/ui/screens/home/HomeScreen.kt |
| Detalle Perfume | app/src/main/java/com/SuperfumeKotlin/ui/screens/perfume/PerfumeDetailScreen.kt |
| Carrito | app/src/main/java/com/SuperfumeKotlin/ui/screens/cart/CartScreen.kt |
| Perfil | app/src/main/java/com/SuperfumeKotlin/ui/screens/profile/ProfileScreen.kt |
| Admin Panel | app/src/main/java/com/SuperfumeKotlin/ui/screens/admin/AdminScreen.kt |
| Gestión Usuarios | app/src/main/java/com/SuperfumeKotlin/ui/screens/admin/UserManagementScreen.kt |
| Agregar Perfume | app/src/main/java/com/SuperfumeKotlin/ui/screens/admin/AddPerfumeScreen.kt |
| ViewModels | app/src/main/java/com/SuperfumeKotlin/ui/viewmodel/ |
| Repositorio | app/src/main/java/com/SuperfumeKotlin/data/repository/ |
| Modelos | app/src/main/java/com/SuperfumeKotlin/data/model/ |
| DTOs | app/src/main/java/com/SuperfumeKotlin/data/model/dto/ |
| Base de datos | app/src/main/java/com/SuperfumeKotlin/data/local/ |
| API Service | app/src/main/java/com/SuperfumeKotlin/data/remote/ApiService.kt |
| Navegación | app/src/main/java/com/SuperfumeKotlin/ui/navigation/SuperfumeNavigation.kt |

## Arquitectura

- MVVM (Model-View-ViewModel)
- Supabase PostgreSQL (Base de datos remota)
- Room Database (Caché local offline)
- Jetpack Compose (UI declarativa)
- Dagger Hilt (Inyección de dependencias)
- Retrofit + OkHttp (Cliente HTTP)
- Material Design 3
- Kotlin Coroutines + Flow

## Compilación

```bash
# Compilar la aplicación
.\gradlew.bat assembleDebug

# Generar APK firmado
.\gradlew.bat assembleRelease

# Limpiar y compilar
.\gradlew.bat clean assembleDebug
```

## Funcionalidades Implementadas

### Autenticación
- Sistema de autenticación con backend Spring Boot
- Login con email y contraseña
- Registro de usuarios con validación de campos
- Persistencia de sesión con EncryptedSharedPreferences
- JWT Token para autenticación

### Sistema de Roles
- Roles de usuario (Admin/Cliente)
- Admin (rol_id = 1): Acceso completo al panel de administración
- Cliente (rol_id = 2): Acceso a catálogo, carrito y perfil
- Navegación basada en permisos

### Catálogo de Perfumes
- Listado de perfumes desde base de datos remota
- Búsqueda de perfumes por nombre
- Filtros por categoría y género
- Detalle completo de perfume

### Gestión de Perfumes (Admin)
- CRUD completo de perfumes
- Agregar perfumes con todos los atributos (nombre, marca, precio, fragancia, notas, perfil)
- Editar perfumes existentes
- Eliminar perfumes
- Gestión de stock

### Gestión de Usuarios (Admin)
- Listar todos los usuarios
- Buscar usuarios por nombre o correo
- Editar información de usuarios
- Cambiar rol de usuarios (Admin/Cliente)
- Eliminar usuarios

### Carrito de Compras
- Agregar perfumes al carrito
- Actualizar cantidades
- Eliminar items del carrito
- Vaciar carrito completo
- Persistencia local con Room
- Cálculo automático de totales


### Navegación
- Navigation Component con Jetpack Compose
- Rutas protegidas según autenticación
- Inicio en HomeScreen

## Configuración de Variables de Entorno

El proyecto usa un archivo `local.properties` para las credenciales sensibles:

```properties
# Supabase Configuration
supabase.url=your_supabase_url
supabase.anon.key=your_supabase_anon_key
supabase.api.url=your_supabase_api_url

# PostgreSQL Connection (Supabase)
supabase.db.host=your_db_host
supabase.db.port=5432
supabase.db.name=postgres
supabase.db.user=postgres
supabase.db.password=your_db_password

# Android SDK
sdk.dir=C\:\\Users\\{user}\\AppData\\Local\\Android\\Sdk
```

### Backend Spring Boot

El backend usa un archivo `.env` para configuración:

```env
# Database
DB_PASSWORD=My_PASSWORD

# JWT Secret
JWT_SECRET=My_Secret
```

## Estructura de Base de Datos (Supabase PostgreSQL)

### Tabla: perfumes
- id (PK, auto-increment)
- nombre
- marca
- precio (int)
- stock (int)
- descripcion
- imagen_url
- genero (Masculino, Femenino, Unisex)
- fragancia (Frescos, Florales, Orientales, Amaderados, Cítricos)
- notas
- perfil

### Tabla: usuarios
- id (PK, auto-increment)
- nombre
- correo
- contrasena (encriptada)
- rut
- telefono
- direccion
- rol_id (FK a roles)
- created_at
- updated_at

### Tabla: roles
- id (PK)
- nombre (Admin, Cliente)

### Tabla: carrito
- id (PK, auto-increment)
- usuario_id (FK a usuarios)
- activo (boolean)
- created_at
- updated_at

### Tabla: carrito_items
- id (PK, auto-increment)
- carrito_id (FK a carrito)
- perfume_id (FK a perfumes)
- cantidad
- precio_unitario

## Arquitectura de Capas

### Capa de Presentación (UI)
- Jetpack Compose
- ViewModels (StateFlow)
- Navigation Component

### Capa de Dominio
- Modelos de datos
- DTOs (Request/Response)
- Validadores de formularios

### Capa de Datos
- Repository Pattern
- ApiService (Retrofit)
- Room Database (DAO)
- TokenManager (EncryptedSharedPreferences)

### Inyección de Dependencias
- Dagger Hilt
- Módulos: DatabaseModule, NetworkModule

## API Endpoints

Ver documentación completa en: `SuperfumeSpringBoot/API_ENDPOINTS.md`

### Principales endpoints:
- POST /api/auth/login
- POST /api/auth/register
- GET /api/perfumes
- POST /api/perfumes (Admin)
- GET /api/usuario (Admin)
- PUT /api/usuario/{id}/rol (Admin)
- GET /api/carrito/usuario/{id}
- POST /api/pedido
- POST /api/pago

## Tecnologías Utilizadas

### Frontend (Kotlin)
- Kotlin 1.9+
- Jetpack Compose
- Material Design 3
- Dagger Hilt
- Room Database
- Retrofit 2
- OkHttp 3
- Kotlin Coroutines
- Kotlin Flow
- Coil (Carga de imágenes)
- EncryptedSharedPreferences

### Backend (Spring Boot)
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL (Supabase)
- JWT Authentication
- Lombok
- Maven

## Configuración del Proyecto

### Requisitos
- Android Studio Hedgehog o superior
- JDK 17
- Gradle 8.0+
- Android SDK 24+ (mínimo)
- Android SDK 34 (target)

### Instalación

1. Clonar el repositorio
```bash
git clone https://github.com/xMvxyz/SuperfumeKotlin.git
git clone https://github.com/xMvxyz/SuperfumeSpringBoot.git
```

2. Configurar `local.properties` en SuperfumeKotlin
```properties
supabase.url=https://your-project.supabase.co
supabase.anon.key=your_anon_key
sdk.dir=C\:\\Users\\{user}\\AppData\\Local\\Android\\Sdk
```

3. Configurar `.env` en SuperfumeSpringBoot
```env
DB_PASSWORD=your_password
JWT_SECRET=your_secret
```

4. Compilar el backend
```bash
cd SuperfumeSpringBoot
mvn clean install
mvn spring-boot:run
```

5. Compilar la app móvil
```bash
cd SuperfumeKotlin
.\gradlew.bat assembleDebug
```

## Ejecución

### Backend
```bash
cd SuperfumeSpringBoot
mvn spring-boot:run
```

### App Móvil
1. Abrir Android Studio
2. Abrir proyecto SuperfumeKotlin
3. Ejecutar en emulador o dispositivo físico
4. O instalar APK:
```bash
adb install app\build\outputs\apk\debug\superfume-arm64-v8a-debug.apk
```

## Estructura del Proyecto

```
SuperfumeKotlin/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/SuperfumeKotlin/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/        # Room Database
│   │   │   │   │   ├── model/        # Modelos y DTOs
│   │   │   │   │   ├── remote/       # API Service
│   │   │   │   │   └── repository/   # Repositorios
│   │   │   │   ├── di/               # Dagger Hilt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── navigation/   # Navegación
│   │   │   │   │   ├── screens/      # Pantallas Compose
│   │   │   │   │   └── viewmodel/    # ViewModels
│   │   │   │   └── util/             # Utilidades
│   │   │   └── res/                  # Recursos
│   │   └── build.gradle.kts
│   └── local.properties
└── README.md

```
## Paso a paso para generar una APK firmada para publicar en Google Play Store o distribuir,:

### 1.Build → Generate Signed Bundle / APK

<img width="340" height="392" alt="image" src="https://github.com/user-attachments/assets/30988c96-817d-42e9-8d01-0cd21be925ee" />

### 2.Selecciona APK

<img width="565" height="395" alt="image" src="https://github.com/user-attachments/assets/079721aa-87f9-45a6-b3f4-a7395a702b5a" />

### 3.Click Next

### 4.Crear nuevo keystore (primera vez):

<img width="561" height="376" alt="image" src="https://github.com/user-attachments/assets/5ebdf1cc-dfba-4cb8-87ac-70adcae26c95" />

### 5.Click Create new

#### Key store path: Elige ubicación (ej: C:\Users\maxim\superfume-keystore.jks)
#### Password: Crea una contraseña segura
#### Alias: superfume-key
#### Password (alias): Misma u otra contraseña
#### Validity: 25 años (mínimo para Play Store)
#### Certificate:
#### First and Last Name: Tu nombre
#### Organization: Tu organización
#### City, State, Country: Tu ubicación
#### Click OK

<img width="524" height="607" alt="image" src="https://github.com/user-attachments/assets/89d85f61-b2ab-4646-803e-1391d36d5e42" />

### 6.Selecciona release

<img width="563" height="392" alt="image" src="https://github.com/user-attachments/assets/a712c290-5777-4147-8058-e05e8bc9eef4" />

### y listo!
