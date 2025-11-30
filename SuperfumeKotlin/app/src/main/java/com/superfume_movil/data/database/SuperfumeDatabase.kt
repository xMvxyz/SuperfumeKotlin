package com.superfume_movil.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.superfume_movil.data.dao.DaoCarrito
import com.superfume_movil.data.dao.DaoPerfume
import com.superfume_movil.data.dao.DaoUsuario
import com.superfume_movil.data.model.ElementoCarrito
import com.superfume_movil.data.model.Perfume
import com.superfume_movil.data.model.Usuario
import com.superfume_movil.util.Constantes

/**
 * Base de datos principal de la aplicación Superfume
 * Utiliza Room para la persistencia local de datos
 */
@Database(
    entities = [Perfume::class, Usuario::class, ElementoCarrito::class],
    version = Constantes.VERSION_BASE_DATOS,
    exportSchema = false
)
abstract class BaseDatosSuperfume : RoomDatabase() {
    abstract fun daoPerfume(): DaoPerfume
    abstract fun daoUsuario(): DaoUsuario
    abstract fun daoCarrito(): DaoCarrito
    
    companion object {
        @Volatile
        private var INSTANCIA: BaseDatosSuperfume? = null
        
        fun obtenerBaseDatos(context: Context): BaseDatosSuperfume {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatosSuperfume::class.java,
                    Constantes.NOMBRE_BASE_DATOS
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}
