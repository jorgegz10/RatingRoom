package com.example.ratingroom.data.repository

import com.example.ratingroom.data.models.Movie
import com.example.ratingroom.data.models.Review
import com.example.ratingroom.data.models.User

object MovieRepository {
    
    private val movies = listOf(
        Movie(
            id = 1,
            title = "Titanic",
            year = "1997",
            genre = "Romance",
            rating = 4.5,
            reviews = 3876,
            description = "Una épica historia de amor ambientada en el trágico viaje del Titanic.",
            director = "James Cameron",
            duration = "3h 14min"
        ),
        Movie(
            id = 2,
            title = "Pulp Fiction",
            year = "1994",
            genre = "Crime",
            rating = 4.9,
            reviews = 3421,
            description = "Historias entrelazadas de crimen en Los Ángeles.",
            director = "Quentin Tarantino",
            duration = "2h 34min"
        ),
        Movie(
            id = 3,
            title = "The Dark Knight",
            year = "2008",
            genre = "Action",
            rating = 4.8,
            reviews = 2987,
            description = "Batman enfrenta al Joker en Gotham City.",
            director = "Christopher Nolan",
            duration = "2h 32min"
        ),
        Movie(
            id = 4,
            title = "Avatar",
            year = "2009",
            genre = "Sci-Fi",
            rating = 4.4,
            reviews = 2987,
            description = "Un marine parapléjico es enviado a la luna Pandora.",
            director = "James Cameron",
            duration = "2h 42min"
        ),
        Movie(
            id = 5,
            title = "Jurassic Park",
            year = "1993",
            genre = "Adventure",
            rating = 4.5,
            reviews = 2654,
            description = "Dinosaurios clonados en un parque temático.",
            director = "Steven Spielberg",
            duration = "2h 7min"
        ),
        Movie(
            id = 6,
            title = "Goodfellas",
            year = "1990",
            genre = "Crime",
            rating = 4.8,
            reviews = 2234,
            description = "La historia de Henry Hill y la mafia italiana.",
            director = "Martin Scorsese",
            duration = "2h 26min"
        ),
        Movie(
            id = 7,
            title = "Inception",
            year = "2010",
            genre = "Sci-Fi",
            rating = 4.7,
            reviews = 4521,
            description = "Un ladrón que roba secretos del subconsciente.",
            director = "Christopher Nolan",
            duration = "2h 28min"
        ),
        Movie(
            id = 8,
            title = "The Shawshank Redemption",
            year = "1994",
            genre = "Drama",
            rating = 4.9,
            reviews = 5432,
            description = "La amistad entre dos prisioneros a lo largo de los años.",
            director = "Frank Darabont",
            duration = "2h 22min"
        )
    )
    
    private val users = listOf(
        User(
            id = 1,
            displayName = "Juan Pérez",
            email = "juan@example.com",
            biography = "Amante del cine clásico y moderno",
            location = "Madrid, España",
            favoriteGenre = "Sci-Fi"
        )
    )
    
    private val reviews = listOf(
        Review(
            id = 1,
            movieId = 1,
            userId = 1,
            rating = 4.5,
            comment = "Una película increíble con efectos visuales impresionantes.",
            date = "2024-01-15"
        ),
        Review(
            id = 2,
            movieId = 2,
            userId = 1,
            rating = 5.0,
            comment = "Obra maestra del cine. Tarantino en su mejor momento.",
            date = "2024-01-10"
        )
    )
    
    fun getAllMovies(): List<Movie> = movies
    
    fun getMoviesByGenre(genre: String): List<Movie> {
        return if (genre == "Todos") movies else movies.filter { it.genre == genre }
    }
    
    fun searchMovies(query: String): List<Movie> {
        return movies.filter { 
            it.title.contains(query, ignoreCase = true) ||
            it.director.contains(query, ignoreCase = true) ||
            it.genre.contains(query, ignoreCase = true)
        }
    }
    
    fun getMovieById(id: Int): Movie? = movies.find { it.id == id }
    
    fun getGenres(): List<String> = listOf("Todos", "Romance", "Crime", "Action", "Sci-Fi", "Adventure", "Drama")
    
    fun getUserById(id: Int): User? = users.find { it.id == id }
    
    fun getReviewsForMovie(movieId: Int): List<Review> = reviews.filter { it.movieId == movieId }
    
    fun getWatchLaterMovies(): List<Movie> {
        // Simulamos películas en "Ver más tarde"
        return movies.take(2)
    }
    
    fun getFavoriteMovies(): List<Movie> {
        // Simulamos películas favoritas
        return movies.filter { it.rating >= 4.7 }
    }
    
    fun getWatchedMovies(): List<Movie> {
        // Simulamos películas vistas
        return movies.takeLast(3)
    }
}