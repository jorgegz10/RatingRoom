# 🎬 RatingRoom

**RatingRoom** es una aplicación Android moderna para descubrir, calificar y reseñar películas, construida con Jetpack Compose y siguiendo el patrón arquitectónico MVVM.

## 📱 Características Principales

### 🎭 Funcionalidades de Usuario
- **Autenticación completa**: Registro, inicio de sesión y recuperación de contraseña
- **Exploración de películas**: Navegación por géneros y búsqueda avanzada
- **Sistema de calificaciones**: Califica películas del 1 al 5 estrellas
- **Reseñas personalizadas**: Escribe y gestiona tus propias reseñas
- **Listas personalizadas**: "Ver más tarde", "Favoritos" y "Vistas"
- **Red social**: Sistema de amigos, seguidores y actividad social
- **Perfil personalizable**: Gestiona tu información y preferencias

### 🎨 Interfaz de Usuario
- **Diseño moderno** con Material Design 3
- **Tema adaptable** con soporte para modo oscuro
- **Navegación intuitiva** con bottom navigation
- **Animaciones fluidas** y transiciones suaves
- **Responsive design** optimizado para diferentes tamaños de pantalla

## 🏗️ Arquitectura

### Patrón MVVM (Model-View-ViewModel)
```
📁 ui/screens/
├── 📁 login/
│   ├── LoginScreen.kt          # Vista (Composable)
│   ├── LoginViewModel.kt       # ViewModel
│   └── LoginUIState.kt         # Estado de UI
├── 📁 main/
│   ├── MainMenuScreen.kt
│   ├── MainMenuViewModel.kt
│   └── MainMenuUIState.kt
└── ...
```

### Componentes Clave
- **ViewModels**: Gestión de estado y lógica de negocio
- **UIState**: Estados reactivos con `StateFlow`
- **Repository Pattern**: Abstracción de fuentes de datos
- **Compose Navigation**: Navegación declarativa
- **Dependency Injection**: Gestión de dependencias

## 📂 Estructura del Proyecto

```
app/src/main/java/com/example/ratingroom/
├── 📁 data/
│   ├── 📁 models/              # Modelos de datos
│   │   ├── Movie.kt
│   │   ├── User.kt
│   │   ├── Friend.kt
│   │   └── Review.kt
│   └── 📁 repository/          # Repositorios
│       ├── MovieRepository.kt
│       └── FriendsRepository.kt
├── 📁 ui/
│   ├── 📁 screens/             # Pantallas principales
│   │   ├── 📁 login/
│   │   ├── 📁 main/
│   │   ├── 📁 profile/
│   │   ├── 📁 friends/
│   │   ├── 📁 reviews/
│   │   └── ...
│   ├── 📁 utils/               # Componentes reutilizables
│   │   ├── MovieCard.kt
│   │   ├── CustomButton.kt
│   │   └── AppTopBar.kt
│   └── 📁 theme/               # Tema y estilos
├── 📁 navigation/              # Navegación
│   └── Screen.kt
└── MainActivity.kt             # Actividad principal
```

## 🛠️ Tecnologías Utilizadas

### Core Android
- **Kotlin** - Lenguaje de programación principal
- **Jetpack Compose** - UI toolkit moderno
- **Material Design 3** - Sistema de diseño
- **Android Architecture Components** - Componentes de arquitectura

### Arquitectura y Estado
- **ViewModel** - Gestión de estado de UI
- **StateFlow** - Estado reactivo
- **Coroutines** - Programación asíncrona
- **Navigation Compose** - Navegación declarativa

### UI y UX
- **Compose Animation** - Animaciones fluidas
- **Custom Composables** - Componentes reutilizables
- **Responsive Layout** - Diseño adaptativo
- **Theme System** - Sistema de temas personalizable

## 🚀 Instalación y Configuración

### Prerrequisitos
- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 17 o superior
- Android SDK API 34
- Gradle 8.0+

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/RatingRoom.git
   cd RatingRoom
   ```

2. **Abrir en Android Studio**
   - Abre Android Studio
   - Selecciona "Open an existing project"
   - Navega hasta la carpeta del proyecto

3. **Sincronizar dependencias**
   ```bash
   ./gradlew build
   ```

4. **Ejecutar la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Presiona el botón "Run" en Android Studio

## 📱 Pantallas Principales

### 🔐 Autenticación
- **LoginScreen**: Inicio de sesión con email/usuario
- **RegisterScreen**: Registro de nuevos usuarios
- **ForgotPasswordScreen**: Recuperación de contraseña

### 🏠 Pantalla Principal
- **MainMenuScreen**: Exploración de películas populares
- **Filtros por género**: Acción, Drama, Comedia, Sci-Fi, etc.
- **Búsqueda avanzada**: Por título, director o género

### 🎬 Gestión de Películas
- **MovieDetailScreen**: Información detallada de películas
- **SynopsisScreen**: Sinopsis completa y reparto
- **ReviewsScreen**: Gestión de reseñas personales

### 👥 Social
- **FriendsScreen**: Gestión de amigos y seguidores
- **ProfileScreen**: Perfil personal y estadísticas
- **FavoritesScreen**: Películas favoritas
- **ListScreen**: Listas personalizadas

### ⚙️ Configuración
- **SettingsScreen**: Preferencias de la aplicación
- **EditProfileScreen**: Edición de perfil personal

## 🎨 Características de UI/UX

### Componentes Personalizados
- **MovieCard**: Tarjetas de películas con información clave
- **StarRating**: Sistema de calificación por estrellas
- **CustomButton**: Botones con estilo personalizado
- **AppTopBar**: Barra superior consistente
- **SearchBar**: Barra de búsqueda con filtros

### Navegación
- **Bottom Navigation**: Navegación principal entre secciones
- **Drawer Navigation**: Menú lateral para opciones adicionales
- **Deep Linking**: Navegación directa a pantallas específicas

## 🔧 Configuración de Desarrollo

### Estructura de Gradle
```kotlin
// build.gradle.kts (Module: app)
dependencies {
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    // ... más dependencias
}
```

### Configuración de Tema
```kotlin
// ui/theme/Theme.kt
@Composable
fun RatingRoomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    // ...
}
```

## 📊 Gestión de Estado

### Ejemplo de ViewModel
```kotlin
class MainMenuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainMenuUIState())
    val uiState: StateFlow<MainMenuUIState> = _uiState.asStateFlow()
    
    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}
```

### Ejemplo de UIState
```kotlin
data class MainMenuUIState(
    val searchQuery: String = "",
    val selectedGenre: String = "Todos",
    val filterExpanded: Boolean = false,
    val isLoading: Boolean = false
)
```

## 🤝 Contribución

### Guías de Contribución
1. **Fork** el repositorio
2. **Crea** una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. **Push** a la rama (`git push origin feature/AmazingFeature`)
5. **Abre** un Pull Request

### Estándares de Código
- Seguir las convenciones de Kotlin
- Usar nombres descriptivos para variables y funciones
- Documentar funciones complejas
- Mantener la consistencia en el estilo de código

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autores

**David Piñeros**
- GitHub: [@DavidSP12](https://github.com/DavidSP12)
- Email: dpineros05@gmail.com

**Daniel Vidal**
- GitHub: [@Dasdoria1O1](https://github.com/Dasdoria1O1)
- Email: davdl0105@gmail.com

**Jorge Gomez**
- GitHub: [@jorgegz10](https://github.com/jorgegz10)
- Email: gzjorgeesteban@yahoo.com


## 🙏 Agradecimientos

- **Material Design** por las guías de diseño
- **Jetpack Compose** por el framework de UI
- **Android Developer Community** por los recursos y documentación

---

⭐ **¡Si te gusta este proyecto, dale una estrella!** ⭐
        
