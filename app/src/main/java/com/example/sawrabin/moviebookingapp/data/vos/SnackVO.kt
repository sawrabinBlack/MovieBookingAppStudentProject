package com.example.sawrabin.moviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "snacks")
data class SnackVO(
    var isSelected: Boolean?,
    @SerializedName("quantity")
    @ColumnInfo(name = "quantity")
    var quantity: Int= 0,
    @SerializedName("id")
    @PrimaryKey
    val id: Int?,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String?,

    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Int?,
    )
