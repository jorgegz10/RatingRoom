package com.example.ratingroom.data.repository

import com.example.ratingroom.data.datasource.AuthRemoteDataSource
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
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)

@Singleton
class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    val currentUser: FirebaseUser? get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val user = authRemoteDataSource.signIn(email, password)
            AuthResult(
                isSuccess = user != null,
                user = user
            )
        } catch (e: Exception) {
            AuthResult(
                isSuccess = false,
                errorMessage = e.message ?: "Error de autenticación"
            )
        }
    }

    suspend fun signUp(
        email: String,
        password: String,
        fullName: String? = null,
        favoriteGenre: String? = null,
        birthYear: String? = null
    ): AuthResult {
        return try {
            println("AuthRepository: Iniciando registro para email: $email")
            val user = authRemoteDataSource.signUp(
                email = email,
                password = password,
                fullName = fullName,
                favoriteGenre = favoriteGenre,
                birthYear = birthYear
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
        website: String? = null
    ): AuthResult {
        return try {
            authRemoteDataSource.updateUserProfile(
                displayName = displayName,
                email = email,
                biography = biography,
                location = location,
                favoriteGenre = favoriteGenre,
                birthdate = birthdate,
                website = website
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
            val user = currentUser ?: return null
            val profileData = authRemoteDataSource.getUserProfile()
            
            if (profileData != null) {
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
                    createdAt = profileData["createdAt"] as? Long,
                    updatedAt = profileData["updatedAt"] as? Long
                )
            } else {
                // Si no hay datos en Firestore, crear perfil básico
                UserProfile(
                    uid = user.uid,
                    email = user.email ?: "",
                    fullName = user.displayName
                )
            }
        } catch (e: Exception) {
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