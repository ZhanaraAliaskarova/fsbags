package com.fourseasonsweb.fsbags.data.room.dao

import android.arch.persistence.room.*
import com.fourseasonsweb.fsbags.data.room.models.OrderEntity

@Dao
interface OrderDao {
    @Insert
    fun insert(vararg sale: OrderEntity)

    @Delete
    fun delete(vararg sale: OrderEntity)

    @Update
    fun update(vararg sale: OrderEntity)

    @Query("SELECT * FROM cart WHERE user = :user")
    fun getAllByUser(user: String): List<OrderEntity>

    @Query("SELECT * FROM cart WHERE productId = :productId AND user = :user")
    fun getSale(productId: Int, user: String): OrderEntity

    @Query("DELETE FROM cart WHERE productId = :productId AND user = :user")
    fun deleteByProductAndUser(productId: Int, user: String)
}