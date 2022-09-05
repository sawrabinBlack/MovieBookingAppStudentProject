package com.example.sawrabin.moviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.sawrabin.moviebookingapp.data.vos.SnackVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SnackPaymentTypeConverter {

    @TypeConverter
    fun toString(snackList: List<SnackVO>?): String {
        return Gson().toJson(snackList)
    }

    @TypeConverter
    fun toSnackPaymentVOList(snackPaymentVOJsonStr: String): List<SnackVO>? {
        val snackPaymentType = object : TypeToken<List<SnackVO>?>() {}.type
        return Gson().fromJson(snackPaymentVOJsonStr, snackPaymentType)
    }
}