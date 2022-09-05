package com.example.sawrabin.moviebookingapp.persistence.daos

import androidx.room.*
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO

@Dao
interface CarrierDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CarrierVO::class)
    fun insertCarrierData(carrierVO: CarrierVO)

    @Query("SELECT * FROM booking_data WHERE id = (SELECT Max(id) FROM booking_data)")
    fun getBookingData(): CarrierVO?

    @Query("UPDATE booking_data SET runtime = :runtime, poster_path= :posterPath , name = :name WHERE id = (SELECT Max(id) FROM booking_data)")
    fun updateBookingDataWithMovieDetail(runtime: String, posterPath: String, name: String)

    @Query("UPDATE booking_data SET cinema_id = :cinemaId , cinema_name= :cinema_name, book_date=:bookDate, timeslot= :timeslot , timeslot_time = :timeslot_time WHERE id = (SELECT Max(id) FROM booking_data)")
    fun updateBookingDataWithTimeSlot(
        cinemaId: Int,
        cinema_name: String,
        bookDate: String,
        timeslot: Int,
        timeslot_time : String,
    )

    @Query("UPDATE booking_data SET `row`= :row, total_price= :totalPrice, seat_number= :seatNumber")
    fun updateBookingDataWithTime(row : String,totalPrice : Int, seatNumber:String)

    @Query("UPDATE booking_data SET snack = :snack , total_price=:totalPrice")
    fun updateBookingDataWithSnack(snack : String, totalPrice: Int)

    @Query("DELETE FROM booking_data")
    fun deleteBookingData()

    @Query("UPDATE booking_data SET booking_no = :bookingNo")
    fun updateBookingDataWithBookingNo(bookingNo:String)




}