package com.fourseasonsweb.fsbags.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.fourseasonsweb.fsbags.data.room.dao.CartDao
import com.fourseasonsweb.fsbags.data.room.dao.OrderDao
import com.fourseasonsweb.fsbags.data.room.dao.ProductDao
import com.fourseasonsweb.fsbags.data.room.models.CartEntity
import com.fourseasonsweb.fsbags.data.room.models.OrderEntity
import com.fourseasonsweb.fsbags.data.room.models.ProductEntity

@Database(entities = arrayOf(ProductEntity::class, CartEntity::class, OrderEntity::class), version = 2)
@TypeConverters(Converters::class)
abstract class BagsDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}