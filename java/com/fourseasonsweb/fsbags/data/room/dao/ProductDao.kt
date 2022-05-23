package com.fourseasonsweb.fsbags.data.room.dao

import android.arch.persistence.room.*
import com.fourseasonsweb.fsbags.data.room.models.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg card: ProductEntity)

    @Delete
    fun delete(vararg card: ProductEntity)

    @Update
    fun update(vararg card: ProductEntity)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProduct(id: Int): ProductEntity
}