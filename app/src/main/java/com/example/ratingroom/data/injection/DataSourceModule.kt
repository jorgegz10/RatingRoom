package com.example.ratingroom.data.injection

import com.example.ratingroom.data.datasource.AuthRemoteDataSource
import com.example.ratingroom.data.datasource.FirestoreDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        authService: FirebaseAuth
    ): AuthRemoteDataSource {
        return AuthRemoteDataSource(authService)
    }

    @Provides
    @Singleton
    fun provideFirestoreDataSource(
        firestoreService: FirebaseFirestore,
        authService: FirebaseAuth
    ): FirestoreDataSource {
        return FirestoreDataSource(firestoreService, authService)
    }
}