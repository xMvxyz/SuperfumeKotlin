package com.SuperfumeKotlin.di

import android.content.Context
import androidx.room.Room
import com.SuperfumeKotlin.data.dao.DaoCarrito
import com.SuperfumeKotlin.data.dao.DaoPerfume
import com.SuperfumeKotlin.data.dao.DaoUsuario
import com.SuperfumeKotlin.data.database.BaseDatosSuperfume
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import com.SuperfumeKotlin.util.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de inyección de dependencias para la base de datos
 * Proporciona instancias de la base de datos, DAOs y repositorio
 */
@Module
@InstallIn(SingletonComponent::class)
object ModuloBaseDatos {
    
    /**
     * Proporciona una instancia de la base de datos
     * @param context Contexto de la aplicación
     * @return Instancia de BaseDatosSuperfume
     */
    @Provides
    @Singleton
    fun proporcionarBaseDatos(@ApplicationContext context: Context): BaseDatosSuperfume {
        return Room.databaseBuilder(
            context.applicationContext,
            BaseDatosSuperfume::class.java,
            Constantes.NOMBRE_BASE_DATOS
        ).build()
    }
    
    /**
     * Proporciona el DAO de perfumes
     * @param baseDatos Instancia de la base de datos
     * @return DAO de perfumes
     */
    @Provides
    fun proporcionarDaoPerfume(baseDatos: BaseDatosSuperfume): DaoPerfume = 
        baseDatos.daoPerfume()
    
    /**
     * Proporciona el DAO de usuarios
     * @param baseDatos Instancia de la base de datos
     * @return DAO de usuarios
     */
    @Provides
    fun proporcionarDaoUsuario(baseDatos: BaseDatosSuperfume): DaoUsuario = 
        baseDatos.daoUsuario()
    
    /**
     * Proporciona el DAO del carrito
     * @param baseDatos Instancia de la base de datos
     * @return DAO del carrito
     */
    @Provides
    fun proporcionarDaoCarrito(baseDatos: BaseDatosSuperfume): DaoCarrito = 
        baseDatos.daoCarrito()
    
    /**
     * Proporciona el repositorio principal
     * @param daoPerfume DAO de perfumes
     * @param daoUsuario DAO de usuarios
     * @param daoCarrito DAO del carrito
     * @return Repositorio principal
     */
    @Provides
    @Singleton
    fun proporcionarRepositorio(
        daoPerfume: DaoPerfume,
        daoUsuario: DaoUsuario,
        daoCarrito: DaoCarrito
    ): RepositorioSuperfume {
        return RepositorioSuperfume(daoPerfume, daoUsuario, daoCarrito)
    }
}
