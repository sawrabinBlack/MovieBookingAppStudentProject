package com.example.sawrabin.moviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sawrabin.moviebookingapp.data.vos.MovieVO

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieVO::class)
    fun insertUser(movie: List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieVO::class)
    fun insertSingleMovie(movie : MovieVO)

    @Query("SELECT * FROM movies WHERE type=:type")
    fun getMoviesByType(type: String): List<MovieVO>

    @Query("SELECT * FROM movies WHERE id= :id")
    fun getMovieByID(id: Int): MovieVO?


    @Query("UPDATE movies SET imdb_rating=:imdbRating WHERE id = :movie_id")
    fun updateImdbRating(imdbRating:Double, movie_id : Int)
}