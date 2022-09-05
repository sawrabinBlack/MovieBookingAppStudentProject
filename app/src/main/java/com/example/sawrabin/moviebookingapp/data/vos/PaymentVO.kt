package com.example.sawrabin.moviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "payments")
data class PaymentVO(
    var isSelected: Boolean?,
    @SerializedName("id")
    @PrimaryKey
    val id: Int?,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String?,)


