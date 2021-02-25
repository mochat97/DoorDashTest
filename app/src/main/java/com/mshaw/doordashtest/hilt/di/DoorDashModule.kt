package com.mshaw.doordashtest.hilt.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DoorDashModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context) = context.getSharedPreferences("doordash", Context.MODE_PRIVATE)
}