package com.mshaw.doordashtest.hilt.di

import com.mshaw.doordashtest.network.restaurant.RestaurantManager
import com.mshaw.doordashtest.network.restaurant.RestaurantService
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.doordash.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun providesRestaurantService(retrofit: Retrofit): RestaurantService = retrofit.create(RestaurantService::class.java)

    @Provides
    @Singleton
    fun providesRestaurantManager(service: RestaurantService) = RestaurantManager(service)
}