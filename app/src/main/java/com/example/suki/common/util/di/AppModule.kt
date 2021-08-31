package com.example.suki.common.util.di


import android.content.Context
import androidx.room.Room
import com.example.suki.common.util.Constant.BASE_URL
import com.example.suki.common.util.Constant.DATABASE_NAME
import com.example.suki.common.data.local.database.AppDatabase
import com.example.suki.common.data.remote.api.AppApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAppApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()
}