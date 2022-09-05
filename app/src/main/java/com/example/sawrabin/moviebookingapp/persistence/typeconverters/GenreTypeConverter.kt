package com.example.sawrabin.moviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.sawrabin.moviebookingapp.data.vos.GenreVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreTypeConverter {

    @TypeConverter
    fun toString(genreList: List<GenreVO>?):String{
        return Gson().toJson(genreList)
    }

    @TypeConverter
    fun toGenreVOList(genreVOJsonStr: String):List<GenreVO>?{
        val genreType= object : TypeToken<List<GenreVO>?>(){}.type
        return Gson().fromJson(genreVOJsonStr,genreType)
    }
}