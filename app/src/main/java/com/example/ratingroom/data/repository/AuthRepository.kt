package com.example.ratingroom.data.repository

data class RecoveryRequest(
    val email: String,
    val timestamp: String,
    val status: String // "sent", "pending", "expired"
)

object AuthRepository {
    
    private val recoveryRequests = mutableListOf<RecoveryRequest>()
    
    fun sendPasswordRecovery(email: String): Boolean {
        // Simular validación de email
        if (isValidEmail(email)) {
            val request = RecoveryRequest(
                email = email,
                timestamp = System.currentTimeMillis().toString(),
                status = "sent"
            )
            recoveryRequests.add(request)
            return true
        }
        return false
    }
    
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
    
    fun getRecoveryRequests(): List<RecoveryRequest> = recoveryRequests
}