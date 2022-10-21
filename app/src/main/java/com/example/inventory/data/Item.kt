package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val price : Double,
    val quantity : Int
)

fun Item.getFormattedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(this.price)
}