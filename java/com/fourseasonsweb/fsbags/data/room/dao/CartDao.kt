package com.fourseasonsweb.fsbags.data.room.dao

import android.arch.persistence.room.*
import com.fourseasonsweb.fsbags.data.room.models.CartEntity

@Dao
interface CartDao {
    @Insert
    fun insert(vararg sale: CartEntity)

    @Delete
    fun delete(vararg sale: CartEntity)

    @Update
    fun update(vararg sale: CartEntity)

    @Query("SELECT * FROM cart WHERE user = :user")
    fun getAllByUser(user: String): List<CartEntity>

    @Query("SELECT * FROM cart WHERE productId = :productId AND user = :user")
    fun getSale(productId: Int, user: String): CartEntity

    @Query("DELETE FROM cart WHERE productId = :productId AND user = :user")
    fun deleteByProductAndUser(productId: Int, user: String)
}