package com.SuperfumeKotlin.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.SuperfumeKotlin.data.dao.DaoCarrito
import com.SuperfumeKotlin.data.dao.DaoPerfume
import com.SuperfumeKotlin.data.dao.DaoUsuario
import com.SuperfumeKotlin.data.database.SuperfumeDatabase
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import com.SuperfumeKotlin.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        perfumeDaoProvider: Provider<DaoPerfume>,
        userDaoProvider: Provider<DaoUsuario>
    ): SuperfumeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SuperfumeDatabase::class.java,
            Constants.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun providePerfumeDao(database: SuperfumeDatabase): DaoPerfume = database.perfumeDao()

    @Provides
    fun provideUserDao(database: SuperfumeDatabase): DaoUsuario = database.userDao()

    @Provides
    fun provideCartDao(database: SuperfumeDatabase): DaoCarrito = database.cartDao()

    @Provides
    @Singleton
    fun provideRepository(
        perfumeDao: DaoPerfume,
        userDao: DaoUsuario,
        cartDao: DaoCarrito,
        apiService: com.SuperfumeKotlin.data.remote.ApiService,
        tokenManager: com.SuperfumeKotlin.util.TokenManager
    ): RepositorioSuperfume {
        return RepositorioSuperfume(perfumeDao, userDao, cartDao, null, apiService, tokenManager)
    }
}