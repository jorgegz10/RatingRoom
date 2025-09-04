package com.example.ratingroom.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: FirebaseAuth,
    private val firestoreService: FirebaseFirestore
) {

    val currentUser: FirebaseUser? get() = authService.currentUser

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val result = authService.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    suspend fun signUp(
        email: String, 
        password: String, 
        fullName: String? = null,
        favoriteGenre: String? = null,
        birthYear: String? = null
    ): FirebaseUser? {
        println("AuthRemoteDataSource: Creando usuario con Firebase Auth")
        val result = authService.createUserWithEmailAndPassword(email, password).await()
        val user = result.user
        println("AuthRemoteDataSource: Usuario creado exitosamente con UID: ${user?.uid}")
        println("AuthRemoteDataSource: Retornando usuario inmediatamente")
        return user
    }

    suspend fun sendPasswordResetEmail(email: String) {
        authService.sendPasswordResetEmail(email).await()
    }

    suspend fun updateUserProfile(
        displayName: String? = null,
        email: String? = null,
        biography: String? = null,
        location: String? = null,
        favoriteGenre: String? = null,
        birthdate: String? = null,
        website: String? = null
    ) {
        val user = currentUser ?: throw IllegalStateException("Usuario no autenticado")
        
        // Actualizar perfil de Firebase Auth si hay cambios en displayName
        displayName?.let {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(it)
                .build()
            user.updateProfile(profileUpdates).await()
        }
        
        // Actualizar email si es diferente
        email?.let { newEmail ->
            if (newEmail != user.email) {
                user.updateEmail(newEmail).await()
            }
        }
        
        // Actualizar información adicional en Firestore
        val updates = mutableMapOf<String, Any>()
        displayName?.let { updates["fullName"] = it }
        email?.let { updates["email"] = it }
        biography?.let { updates["biography"] = it }
        location?.let { updates["location"] = it }
        favoriteGenre?.let { updates["favoriteGenre"] = it }
        birthdate?.let { updates["birthdate"] = it }
        website?.let { updates["website"] = it }
        updates["updatedAt"] = System.currentTimeMillis()
        
        if (updates.isNotEmpty()) {
            firestoreService.collection("users")
                .document(user.uid)
                .update(updates)
                .await()
        }
    }

    suspend fun getUserProfile(): Map<String, Any>? {
        val user = currentUser ?: return null
        
        return try {
            val document = firestoreService.collection("users")
                .document(user.uid)
                .get()
                .await()
            
            if (document.exists()) {
                document.data
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun signOut() {
        authService.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }
}