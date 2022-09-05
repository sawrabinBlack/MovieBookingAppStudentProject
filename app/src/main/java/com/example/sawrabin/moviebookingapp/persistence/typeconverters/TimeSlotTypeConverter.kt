package com.example.sawrabin.moviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.sawrabin.moviebookingapp.data.vos.TimeSlotVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TimeSlotTypeConverter {
    @TypeConverter
    fun toString(timeslotList:List<TimeSlotVO>?): String{
        return Gson().toJson(timeslotList)
    }

    @TypeConverter
    fun toTimeslotVOList(timeslotListJsonStr:String):List<TimeSlotVO>?{
        val timeslotType= object: TypeToken<List<TimeSlotVO>?>(){}.type
        return Gson().fromJson(timeslotListJsonStr,timeslotType)
    }
}