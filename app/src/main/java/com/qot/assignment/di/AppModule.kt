package com.qot.assignment.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.qot.assignment.BuildConfig
import com.qot.assignment.data.local.AppDatabase
import com.qot.assignment.data.remote.ApiService
import com.qot.assignment.data.remote.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val READ_TIMEOUT_SECONDS = 60L
    private const val CONNECTION_TIMEOUT_SECONDS = 60L


    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "assignment-db"
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(appDb: AppDatabase) = appDb.userDao()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun buildOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(AuthInterceptor())
            .build()

}