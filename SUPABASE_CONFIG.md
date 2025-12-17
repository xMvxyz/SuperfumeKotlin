# Guía de Configuración de Supabase

## 📋 Pasos para Configurar

### 1. Obtener Credenciales de Supabase

1. Ve a [Supabase Dashboard](https://app.supabase.com)
2. Selecciona tu proyecto
3. Ve a **Settings** → **API**
4. Copia los siguientes valores:
   - **Project URL**: `https://xxxxxxxxxxx.supabase.co`
   - **anon/public key**: La clave pública (empieza con `eyJhbGciOi...`)

### 2. Configurar local.properties

1. Copia el archivo de ejemplo:
   ```bash
   cp local.properties.example local.properties
   ```

2. Edita `local.properties` con tus credenciales:
   ```properties
   supabase.url=https://tu-proyecto.supabase.co
   supabase.anon.key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.ey...
   supabase.api.url=https://tu-proyecto.supabase.co/rest/v1
   ```

3. **IMPORTANTE**: `local.properties` ya está en `.gitignore` y NO se subirá a Git

### 3. Configurar PostgreSQL (Opcional)

Si necesitas conexión directa a PostgreSQL:

1. Ve a **Settings** → **Database** en Supabase
2. Copia las credenciales de conexión
3. Agrégalas a `local.properties`:
   ```properties
   supabase.db.host=db.tu-proyecto.supabase.co
   supabase.db.port=5432
   supabase.db.name=postgres
   supabase.db.user=postgres
   supabase.db.password=tu_password_aqui
   ```

### 4. Inicializar en la App

La configuración se carga automáticamente desde `SupabaseConfig.kt`.

Para usarla en tu código:

```kotlin
// Obtener URL de Supabase
val url = SupabaseConfig.supabaseUrl

// Obtener Anon Key
val key = SupabaseConfig.supabaseAnonKey

// Verificar si está configurado
if (SupabaseConfig.isConfigured()) {
    // Configuración lista
}
```

## 🔒 Seguridad

### ✅ Seguro para el Cliente Móvil
- **Anon Key**: Sí, es segura para usar en el cliente
- **Project URL**: Sí, es pública

### ❌ NO usar en el Cliente Móvil
- **Service Role Key**: Solo para backend (Spring Boot)
- **Database Password**: Solo para backend

## 🔗 Integración con Spring Boot

Para tu backend Spring Boot, usa las mismas credenciales en `application.properties`:

```properties
# Supabase PostgreSQL
spring.datasource.url=jdbc:postgresql://db.tu-proyecto.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=tu_password_aqui
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 📝 Archivos Creados

```
SuperfumeKotlin/
├── local.properties.example     ← Plantilla (SÍ se sube a Git)
├── local.properties             ← Tus credenciales (NO se sube a Git)
└── app/src/main/java/com/SuperfumeKotlin/util/
    └── SupabaseConfig.kt        ← Clase para leer configuración
```

## 🚀 Próximos Pasos

1. **Copia `local.properties.example` a `local.properties`**
2. **Completa tus credenciales de Supabase**
3. **Actualiza `NetworkModule.kt` para usar `SupabaseConfig.supabaseApiUrl`**
4. **Configura tu Spring Boot con las mismas credenciales**

## ❓ Preguntas Frecuentes

**P: ¿Dónde encuentro mi Project URL?**  
R: Supabase Dashboard → Settings → API → Project URL

**P: ¿Qué clave uso en el móvil?**  
R: La **anon/public key** (es segura para el cliente)

**P: ¿Puedo usar la Service Role Key en el móvil?**  
R: **NO**, solo úsala en el backend (Spring Boot)

**P: ¿Cómo conecto Spring Boot a Supabase?**  
R: Usa las credenciales de PostgreSQL en `application.properties`
