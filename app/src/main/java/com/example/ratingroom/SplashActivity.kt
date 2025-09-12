package com.example.ratingroom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ratingroom.ui.theme.RatingRoomTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Instalar splash screen nativo para Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        }
        
        super.onCreate(savedInstanceState)
        
        setContent {
            RatingRoomTheme {
                SplashScreen {
                    // Navegar a MainActivity después del splash
                    navigateToMainActivity()
                }
            }
        }
    }
    
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun SplashScreen(onNavigate: () -> Unit) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.5f) }
    
    LaunchedEffect(Unit) {
        // Animación de fade in y scale
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
        
        // Esperar un poco más antes de navegar
        delay(1200)
        
        // Animación de fade out
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 400)
        )
        
        // Llamar al callback para navegar
        onNavigate()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF415EC2),
                        Color(0xFF19243A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .alpha(alpha.value)
                .scale(scale.value)
        ) {
            // Logo de la aplicación
            Image(
                painter = painterResource(id = R.drawable.logoratingroom),
                contentDescription = "RatingRoom Logo",
                modifier = Modifier.size(120.dp)
            )
            
            // Nombre de la aplicación
            Text(
                text = "RatingRoom",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            
            // Subtítulo
            Text(
                text = "Descubre, Califica, Comparte",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}