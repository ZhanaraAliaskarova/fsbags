package com.fourseasonsweb.fsbags.data.room.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int = 0,
    @ColumnInfo(name = "productId")
    var ProductId: Int = 0,
    @ColumnInfo(name = "user")
    var User: String = "",
    @ColumnInfo(name = "regDate")
    var Timestamp: Date = Date()
)
{}