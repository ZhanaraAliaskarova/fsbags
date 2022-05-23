package com.fourseasonsweb.fsbags.data.room.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int,
    @ColumnInfo(name = "name")
    var Name: String = "",
    @ColumnInfo(name = "description")
    var Description: String = "",
    @ColumnInfo(name = "amount")
    var price: Double = 0.0,
    @ColumnInfo(name = "image")
    var Image: Int = 0)
{}