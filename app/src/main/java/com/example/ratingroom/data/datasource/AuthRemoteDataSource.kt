package com.example.ratingroom.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: FirebaseAuth
) {

    val currentUser: FirebaseUser? get() = authService.currentUser

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val result = authService.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    suspend fun signUp(
        email: String, 
        password: String, 
        displayName: String? = null
    ): FirebaseUser? {
        println("AuthRemoteDataSource: Creando usuario con Firebase Auth")
        val result = authService.createUserWithEmailAndPassword(email, password).await()
        val user = result.user
        
        // Actualizar el displayName si se proporciona
        displayName?.let {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(it)
                .build()
            user?.updateProfile(profileUpdates)?.await()
        }
        
        println("AuthRemoteDataSource: Usuario creado exitosamente con UID: ${user?.uid}")
        return user
    }

    suspend fun sendPasswordResetEmail(email: String) {
        authService.sendPasswordResetEmail(email).await()
    }

    suspend fun updateUserEmail(newEmail: String) {
        val user = currentUser ?: throw IllegalStateException("Usuario no autenticado")
        if (newEmail != user.email) {
            println("AuthRemoteDataSource.updateUserEmail: Actualizando email en Firebase Auth: $newEmail")
            user.updateEmail(newEmail).await()
            println("AuthRemoteDataSource.updateUserEmail: Email actualizado en Firebase Auth")
        }
    }
    
    suspend fun updateDisplayName(displayName: String) {
        val user = currentUser ?: throw IllegalStateException("Usuario no autenticado")
        println("AuthRemoteDataSource.updateDisplayName: Actualizando displayName en Firebase Auth: $displayName")
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        user.updateProfile(profileUpdates).await()
        println("AuthRemoteDataSource.updateDisplayName: displayName actualizado en Firebase Auth")
    }

    fun signOut() {
        authService.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }
}