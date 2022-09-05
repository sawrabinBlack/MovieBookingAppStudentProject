package com.example.sawrabin.moviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sawrabin.moviebookingapp.data.vos.PaymentVO
import com.example.sawrabin.moviebookingapp.data.vos.SnackVO
@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = PaymentVO::class)
    fun insertPayments(snack : List<PaymentVO>)

    @Query("SELECT * FROM payments")
    fun getPayments():List<PaymentVO>
}
