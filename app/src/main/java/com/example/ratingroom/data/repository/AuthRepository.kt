package com.example.ratingroom.data.repository

import com.example.ratingroom.data.datasource.AuthRemoteDataSource
import com.example.ratingroom.data.datasource.FirestoreDataSource
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

data class AuthResult(
    val isSuccess: Boolean,
    val user: FirebaseUser? = null,
    val errorMessage: String? = null
)

data class UserProfile(
    val uid: String,
    val email: String,
    val fullName: String? = null,
    val favoriteGenre: String? = null,
    val birthYear: String? = null,
    val biography: String? = null,
    val location: String? = null,
    val birthdate: String? = null,
    val website: String? = null,
    val profileImageUrl: String? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)

@Singleton
class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val firestoreDataSource: FirestoreDataSource
) {

    val currentUser: FirebaseUser? get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val user = authRemoteDataSource.signIn(email, password)
            AuthResult(
                isSuccess = user != null,
                user = user
            )
        } catch (e: FirebaseAuthActionCodeException){
            AuthResult(
                isSuccess = false,
                errorMessage = "contrasena invalida" ?: "Error de autenticación"
            )
        }
        catch (e: Exception) {
            AuthResult(
                isSuccess = false,
                errorMessage = e.message ?: "Error de autenticación"
            )
        }
    }

    suspend fun signUp(
        email: String,
        password: String,
        displayName: String? = null
    ): AuthResult {
        return try {
            println("AuthRepository: Iniciando registro para email: $email")
            val user = authRemoteDataSource.signUp(
                email = email,
                password = password,
                displayName = displayName
            )
            println("AuthRepository: Usuario registrado: ${user?.uid}")
            val result = AuthResult(
                isSuccess = user != null,
                user = user
            )
            println("AuthRepository: Resultado isSuccess: ${result.isSuccess}")
            result
        } catch (e: Exception) {
            println("AuthRepository: Error en registro: ${e.message}")
            e.printStackTrace()
            AuthResult(
                isSuccess = false,
                errorMessage = e.message ?: "Error al crear cuenta"
            )
        }
    }

    suspend fun sendPasswordResetEmail(email: String): AuthResult {
        return try {
            authRemoteDataSource.sendPasswordResetEmail(email)
            AuthResult(isSuccess = true)
        } catch (e: Exception) {
            AuthResult(
                isSuccess = false,
                errorMessage = e.message ?: "Error al enviar email de recuperación"
            )
        }
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
    ): AuthResult {
        return try {
            // Actualizar en Firebase Auth si es necesario
            displayName?.let { authRemoteDataSource.updateDisplayName(it) }
            email?.let { authRemoteDataSource.updateUserEmail(it) }
            
            // Actualizar en Firestore
            firestoreDataSource.updateUserProfile(
                displayName = displayName,
                email = email,
                biography = biography,
                location = location,
                favoriteGenre = favoriteGenre,
                birthdate = birthdate,
                website = website,
                profileImageUrl = profileImageUrl
            )
            AuthResult(isSuccess = true)
        } catch (e: Exception) {
            AuthResult(
                isSuccess = false,
                errorMessage = e.message ?: "Error al actualizar perfil"
            )
        }
    }

    suspend fun getUserProfile(): UserProfile? {
        return try {
            println("AuthRepository.getUserProfile: Iniciando obtención de perfil")
            val user = currentUser ?: return null
            println("AuthRepository.getUserProfile: Usuario autenticado: ${user.uid}")
            
            val profileData = firestoreDataSource.getUserProfile()
            println("AuthRepository.getUserProfile: Datos recibidos de FirestoreDataSource: ${profileData != null}")
            
            if (profileData != null) {
                val profileImageUrl = profileData["profileImageUrl"] as? String
                println("AuthRepository.getUserProfile: profileImageUrl recuperado: $profileImageUrl")
                
                UserProfile(
                    uid = user.uid,
                    email = user.email ?: "",
                    fullName = profileData["fullName"] as? String,
                    favoriteGenre = profileData["favoriteGenre"] as? String,
                    birthYear = profileData["birthYear"] as? String,
                    biography = profileData["biography"] as? String,
                    location = profileData["location"] as? String,
                    birthdate = profileData["birthdate"] as? String,
                    website = profileData["website"] as? String,
                    profileImageUrl = profileImageUrl,
                    createdAt = profileData["createdAt"] as? Long,
                    updatedAt = profileData["updatedAt"] as? Long
                ).also {
                    println("AuthRepository.getUserProfile: Perfil creado con profileImageUrl: ${it.profileImageUrl}")
                }
            } else {
                // Si no hay datos en Firestore, crear perfil básico
                println("AuthRepository.getUserProfile: No hay datos en Firestore, creando perfil básico")
                UserProfile(
                    uid = user.uid,
                    email = user.email ?: "",
                    fullName = user.displayName
                )
            }
        } catch (e: Exception) {
            println("AuthRepository.getUserProfile: ERROR al obtener perfil: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return authRemoteDataSource.isUserLoggedIn()
    }
}