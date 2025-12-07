package com.SuperfumeKotlin.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.SuperfumeKotlin.data.dao.DaoCarrito
import com.SuperfumeKotlin.data.dao.DaoPerfume
import com.SuperfumeKotlin.data.dao.DaoUsuario
import com.SuperfumeKotlin.data.model.ElementoCarrito
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.util.Constants

/**
 * Base de datos principal de la aplicación Superfume
 * Utiliza Room para la persistencia local de datos
 */
@Database(
    entities = [Perfume::class, Usuario::class, ElementoCarrito::class],
    version = Constants.DATABASE_VERSION,
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
                    Constants.DATABASE_NAME
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}
