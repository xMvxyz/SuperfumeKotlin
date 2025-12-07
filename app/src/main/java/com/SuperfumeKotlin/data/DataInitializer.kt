package com.SuperfumeKotlin.data

import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Inicializador de datos de la aplicación Superfume
 * Se encarga de cargar datos de ejemplo en la base de datos
 */
@Singleton
class InicializadorDatos @Inject constructor(
    private val repositorio: RepositorioSuperfume
) {
    
    /**
     * Inicializa los datos de ejemplo en la base de datos
     * Carga una colección de perfumes de muestra
     */
    fun inicializarDatos() {
        CoroutineScope(Dispatchers.IO).launch {
            val perfumesEjemplo = listOf(
                Perfume(
                    name = "Chanel No. 5",
                    brand = "Chanel",
                    price = 120000,
                    description = "Un clásico atemporal con notas florales y aldehídicas. Una fragancia elegante y sofisticada que ha sido amada por generaciones.",
                    category = "Florales",
                    size = "100ml",
                    gender = "Femenino",
                    stock = 15
                ),
                Perfume(
                    name = "Dior Sauvage",
                    brand = "Dior",
                    price = 95000,
                    description = "Una fragancia fresca y moderna con notas de bergamota y pimienta negra. Perfecta para el hombre contemporáneo.",
                    category = "Frescos",
                    size = "100ml",
                    gender = "Masculino",
                    stock = 20
                ),
                Perfume(
                    name = "Tom Ford Black Orchid",
                    brand = "Tom Ford",
                    price = 180000,
                    description = "Una fragancia misteriosa y seductora con notas de orquídea negra, chocolate y pachulí. Para ocasiones especiales.",
                    category = "Orientales",
                    size = "50ml",
                    gender = "Unisex",
                    stock = 8
                ),
                Perfume(
                    name = "Versace Eros",
                    brand = "Versace",
                    price = 75000,
                    description = "Una fragancia intensa y apasionada con notas de menta, manzana verde y vainilla. Para el hombre audaz.",
                    category = "Amaderados",
                    size = "100ml",
                    gender = "Masculino",
                    stock = 12
                ),
                Perfume(
                    name = "Marc Jacobs Daisy",
                    brand = "Marc Jacobs",
                    price = 85000,
                    description = "Una fragancia juvenil y alegre con notas de violeta, gardenia y almizcle blanco. Perfecta para el día a día.",
                    category = "Florales",
                    size = "75ml",
                    gender = "Femenino",
                    stock = 18
                ),
                Perfume(
                    name = "Acqua di Gio",
                    brand = "Giorgio Armani",
                    price = 90000,
                    description = "Una fragancia acuática y fresca inspirada en el mar Mediterráneo. Ideal para el verano.",
                    category = "Cítricos",
                    size = "100ml",
                    gender = "Masculino",
                    stock = 25
                ),
                Perfume(
                    name = "Yves Saint Laurent Libre",
                    brand = "YSL",
                    price = 110000,
                    description = "Una fragancia libre y audaz con notas de lavanda, naranja amarga y vainilla. Para la mujer independiente.",
                    category = "Florales",
                    size = "90ml",
                    gender = "Femenino",
                    stock = 14
                ),
                Perfume(
                    name = "Hermès Terre d'Hermès",
                    brand = "Hermès",
                    price = 140000,
                    description = "Una fragancia terrosa y sofisticada con notas de naranja, pimienta y cedro. Para el hombre refinado.",
                    category = "Amaderados",
                    size = "100ml",
                    gender = "Masculino",
                    stock = 10
                ),
                Perfume(
                    name = "Jo Malone Lime Basil & Mandarin",
                    brand = "Jo Malone",
                    price = 65000,
                    description = "Una fragancia cítrica y herbácea con notas de lima, albahaca y mandarina. Fresca y energizante.",
                    category = "Cítricos",
                    size = "30ml",
                    gender = "Unisex",
                    stock = 22
                ),
                Perfume(
                    name = "Lancôme La Vie Est Belle",
                    brand = "Lancôme",
                    price = 105000,
                    description = "Una fragancia dulce y femenina con notas de iris, vainilla y praliné. Para celebrar la vida.",
                    category = "Orientales",
                    size = "75ml",
                    gender = "Femenino",
                    stock = 16
                )
            )
            
            // Insertar perfumes de ejemplo
            perfumesEjemplo.forEach { perfume ->
                repositorio.insertarPerfume(perfume)
            }
            
            // Insertar usuarios de ejemplo
            val usuariosEjemplo = listOf(
                Usuario(
                    email = "admin@superfume.com",
                    password = "admin123",
                    firstName = "Administrador",
                    lastName = "Sistema",
                    phone = "+1234567890",
                    address = "123 Admin Street, Ciudad"
                ),
                Usuario(
                    email = "usuario@test.com",
                    password = "test123",
                    firstName = "Usuario",
                    lastName = "Prueba",
                    phone = "+0987654321",
                    address = "456 Test Avenue, Ciudad"
                ),
                Usuario(
                    email = "maria@example.com",
                    password = "maria123",
                    firstName = "María",
                    lastName = "García",
                    phone = "+1122334455",
                    address = "789 María Lane, Ciudad"
                )
            )
            
            usuariosEjemplo.forEach { usuario ->
                repositorio.insertarUsuario(usuario)
            }
        }
    }
}
