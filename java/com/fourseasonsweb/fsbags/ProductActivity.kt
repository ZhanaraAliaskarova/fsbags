package com.fourseasonsweb.fsbags

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.content_product.*


class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        setSupportActionBar(toolbar)

        //Получаем идентификатор товара переданного из предыдущего activity
        init(intent.getStringExtra("productId").toInt())
    }

    //Инициализация данных
    private fun init(productId: Int) {
        //Достать из базы данных продукт по идентификатору
        val product = MainActivity.database!!.productDao().getProduct(productId)

        //Установить описание товара
        description.setText(product.Description)
        //Установить свойство textView isEnabled
        description.isEnabled = false

        //Загрузить фотографию (Используем открытую библиотеку Glide)
        try {
            Glide.with(this).load(product.Image).into(findViewById(R.id.backdrop) as ImageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //Установить наименование товара как title Activity
        setTitle(product.Name)
    }
}
