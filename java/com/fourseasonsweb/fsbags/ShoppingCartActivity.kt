package com.fourseasonsweb.fsbags

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.widget.Toast
import com.fourseasonsweb.fsbags.adapter.ShoppingCartAdapter
import com.fourseasonsweb.fsbags.data.Product
import com.fourseasonsweb.fsbags.data.room.models.OrderEntity
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.content_shopping_cart.*
import java.util.*

class ShoppingCartActivity : AppCompatActivity() {

    private var adapter: ShoppingCartAdapter? = null
    private var recyclerView: RecyclerView? =null
    private var productList: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        setSupportActionBar(toolbar)

        initRecyclerView()
        fillProductList()

        create_order_button.setOnClickListener { createOrder() }
    }


    /**
    Инициализировать RecyclerView (где отображаем список товара добавленого в карту)
     */
    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_cart)
        val mLayoutManager = GridLayoutManager(this, 1)
        productList = ArrayList()
        adapter = ShoppingCartAdapter(this, productList)

        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(1, dpToPx(10), true))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter
    }

    /**
    Заполняем список товара из карты пользователся
     */
    private fun fillProductList(){
        productList.clear()
        var userName = ""
        //Достать email пользователся
        if (MainActivity.userName != null) userName = MainActivity.userName!!
        //Достать список товара из базы данных добавленного в корзину
        val cartList = MainActivity.database!!.cartDao().getAllByUser(userName)

        for (item in cartList)
        {
            //Достать из базы продукт по идентификатору из карты
            val product = MainActivity.database!!.productDao().getProduct(item.ProductId)
            //добавить в массив который передаем в адаптер
            productList.add(Product(product.Id, product.Name, product.Description, product.Image, product.price))
        }

        //Оповещаем адаптер об изменениях данных
        adapter!!.notifyDataSetChanged()
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    /**
    Событие для кнопки "Разместить заказ"
    Добавляем в таблицу order список товара для доставки
    и очищаем корзину пользователя
     */
    private fun createOrder() {
        //Получить email пользователя
        val userName = MainActivity.userName!!
        //Получить список товара из корзины
        val cartList = MainActivity.database!!.cartDao().getAllByUser(userName)

        for (item in cartList) {
            //добавить заказ в таблицу order
            MainActivity.database!!.orderDao().insert(OrderEntity(0, item.ProductId, userName, Date()))
            //Удалить товар из корзины
            MainActivity.database!!.cartDao().delete(item)
        }
        //Выйти в главное меню
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
        //Оповестить пользователя об успешном размешении заказа
        Toast.makeText(this, "Заказ успешно оформлен", Toast.LENGTH_SHORT).show()

    }
}
