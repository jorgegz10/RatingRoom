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
        website: String? = null,
        profileImageUrl: String? = null
    ) {
        println("AuthRemoteDataSource.updateUserProfile: Iniciando actualización de perfil")
        println("AuthRemoteDataSource.updateUserProfile: profileImageUrl recibido: $profileImageUrl")
        
        val user = currentUser ?: throw IllegalStateException("Usuario no autenticado")
        println("AuthRemoteDataSource.updateUserProfile: Usuario autenticado: ${user.uid}")
        
        // Actualizar perfil de Firebase Auth si hay cambios en displayName
        displayName?.let {
            println("AuthRemoteDataSource.updateUserProfile: Actualizando displayName en Firebase Auth: $it")
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(it)
                .build()
            user.updateProfile(profileUpdates).await()
            println("AuthRemoteDataSource.updateUserProfile: displayName actualizado en Firebase Auth")
        }
        
        // Actualizar email si es diferente
        email?.let { newEmail ->
            if (newEmail != user.email) {
                println("AuthRemoteDataSource.updateUserProfile: Actualizando email en Firebase Auth: $newEmail")
                user.updateEmail(newEmail).await()
                println("AuthRemoteDataSource.updateUserProfile: Email actualizado en Firebase Auth")
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
        profileImageUrl?.let { 
            println("AuthRemoteDataSource.updateUserProfile: Añadiendo profileImageUrl a updates: $it")
            updates["profileImageUrl"] = it 
        }
        updates["updatedAt"] = System.currentTimeMillis()
        
        println("AuthRemoteDataSource.updateUserProfile: Campos a actualizar en Firestore: ${updates.keys.joinToString()}")
        
        if (updates.isNotEmpty()) {
            println("AuthRemoteDataSource.updateUserProfile: Actualizando documento en Firestore para usuario: ${user.uid}")
            try {
                // Verificar si el documento existe
                val docRef = firestoreService.collection("users").document(user.uid)
                val docSnapshot = docRef.get().await()
                
                if (docSnapshot.exists()) {
                    println("AuthRemoteDataSource.updateUserProfile: Documento existe, actualizando...")
                    docRef.update(updates).await()
                    println("AuthRemoteDataSource.updateUserProfile: Documento actualizado exitosamente en Firestore")
                } else {
                    println("AuthRemoteDataSource.updateUserProfile: Documento NO existe, creando nuevo documento...")
                    docRef.set(updates).await()
                    println("AuthRemoteDataSource.updateUserProfile: Documento creado exitosamente en Firestore")
                }
            } catch (e: Exception) {
                println("AuthRemoteDataSource.updateUserProfile: ERROR al actualizar documento en Firestore: ${e.message}")
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun getUserProfile(): Map<String, Any>? {
        val user = currentUser ?: return null
        println("AuthRemoteDataSource.getUserProfile: Obteniendo perfil para usuario: ${user.uid}")
        
        return try {
            val document = firestoreService.collection("users")
                .document(user.uid)
                .get()
                .await()
            
            if (document.exists()) {
                println("AuthRemoteDataSource.getUserProfile: Documento encontrado en Firestore")
                val data = document.data
                println("AuthRemoteDataSource.getUserProfile: Datos recuperados: ${data?.keys?.joinToString()}")
                println("AuthRemoteDataSource.getUserProfile: profileImageUrl: ${data?.get("profileImageUrl")}")
                data
            } else {
                println("AuthRemoteDataSource.getUserProfile: Documento NO existe en Firestore para usuario: ${user.uid}")
                null
            }
        } catch (e: Exception) {
            println("AuthRemoteDataSource.getUserProfile: ERROR al obtener perfil: ${e.message}")
            e.printStackTrace()
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