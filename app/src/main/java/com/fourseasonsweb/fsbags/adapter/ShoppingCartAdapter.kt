package com.fourseasonsweb.fsbags.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fourseasonsweb.fsbags.MainActivity
import com.fourseasonsweb.fsbags.ProductActivity
import com.fourseasonsweb.fsbags.R
import com.fourseasonsweb.fsbags.data.Product

class ShoppingCartAdapter(private var mContext: Context, private var productList: ArrayList<Product>)
    : RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var price: TextView
        var thumbnail: ImageView
        var overflow: ImageView

        init {
            name = view.findViewById(R.id.tv_name)
            price = view.findViewById(R.id.tv_price)
            thumbnail = view.findViewById(R.id.iv_thumbnail) as ImageView
            overflow = view.findViewById(R.id.iv_overflow) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.getName()
        holder.price.text = "Цена: ${product.getPrice()}"

        //Устанавливаем картинку
        Glide.with(mContext).load(product.getImage()).into(holder.thumbnail)

        //Событие при нажатии на три кнопки
        holder.overflow.setOnClickListener { showPopupMenu(holder.overflow, product) }
    }

    /**
     * Показать popup menu при нажатии на три точки
     */
    private fun showPopupMenu(view: View, product: Product) {
        // inflate menu
        val popup = PopupMenu(mContext, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_cart_product, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener(product))
        popup.show()
    }

    /**
     * Listener для трех точек
     */
    internal inner class MyMenuItemClickListener(private var product: Product) : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                //Открыть продукт
                R.id.action_open_product -> {
                    val intent = Intent(mContext, ProductActivity::class.java)
                    intent.putExtra("productId", product.getId())
                    mContext.startActivity(intent)
                    return true
                }
                //Удалить из корзины
                R.id.action_delete -> {
                    val userName = MainActivity.userName!!
                    MainActivity.database!!.cartDao().deleteByProductAndUser(product.getId(), userName)
                    return true
                }
            }
            return false
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}