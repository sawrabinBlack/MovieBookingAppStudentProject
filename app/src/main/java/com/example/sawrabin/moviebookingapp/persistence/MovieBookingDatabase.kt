package com.example.sawrabin.moviebookingapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sawrabin.moviebookingapp.data.vos.*
import com.example.sawrabin.moviebookingapp.persistence.daos.*

@Database(
    entities = [UserDataVO::class, MovieVO::class, CarrierVO::class,SnackVO::class,PaymentVO::class,CinemaDayVO::class],
    exportSchema = false,
    version = 6
)
abstract class MovieBookingDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "MOVIE_BOOKING_DB"
        var dbInstance: MovieBookingDatabase? = null

        fun getInstance(context: Context): MovieBookingDatabase? {
            when (dbInstance) {
                null -> {
                    dbInstance =
                        Room.databaseBuilder(context, MovieBookingDatabase::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return dbInstance
        }

    }

    abstract fun userDataDao(): UserDataDao
    abstract fun movieDao(): MovieDao
    abstract fun carrierDao(): CarrierDao
    abstract fun snackDao(): SnackDao
    abstract fun paymentDao(): PaymentDao
    abstract fun timeSlotDao():TimeSlotDao
}