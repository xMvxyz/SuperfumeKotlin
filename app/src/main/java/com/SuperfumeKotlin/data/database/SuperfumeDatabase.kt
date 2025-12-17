package com.SuperfumeKotlin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
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
abstract class SuperfumeDatabase : RoomDatabase() {
    abstract fun perfumeDao(): DaoPerfume
    abstract fun userDao(): DaoUsuario
    abstract fun cartDao(): DaoCarrito
}