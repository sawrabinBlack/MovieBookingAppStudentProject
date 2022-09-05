package com.example.sawrabin.moviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CardTypeConverter {

    @TypeConverter
    fun toString(cardList: List<CardVO>?): String {
        return Gson().toJson(cardList)
    }

    @TypeConverter
    fun toCardVO(cardVoJsonStr: String):List<CardVO>? {
        val cardTypeToken = object : TypeToken<List<CardVO>?>() {}.type
        return Gson().fromJson(cardVoJsonStr, cardTypeToken)
    }

}