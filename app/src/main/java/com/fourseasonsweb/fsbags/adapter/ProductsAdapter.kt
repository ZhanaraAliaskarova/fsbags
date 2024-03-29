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
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fourseasonsweb.fsbags.MainActivity
import com.fourseasonsweb.fsbags.ProductActivity
import com.fourseasonsweb.fsbags.R
import com.fourseasonsweb.fsbags.data.Product
import com.fourseasonsweb.fsbags.data.room.models.CartEntity
import java.util.*

class ProductsAdapter(private val mContext: Context, private val productList: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var count: TextView
        var thumbnail: ImageView
        var overflow: ImageView

        init {
            title = view.findViewById(R.id.title)
            count = view.findViewById(R.id.count)
            thumbnail = view.findViewById(R.id.thumbnail) as ImageView
            overflow = view.findViewById(R.id.overflow) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_product, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]
        var description = product.getDescription()
        if (description.length > 50) description = description.substring(0, 50) + "..."

        holder.title.setText(product.getName())
        holder.count.setText(description)

        Glide.with(mContext)
            .load(product.getImage())
            .override(100, 100)
            //.centerCrop()
            .into(holder.thumbnail)

        //Устанавливаем событие для картинки с тремя точками
        holder.overflow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                showPopupMenu(holder.overflow, product)
            }
        })
    }

    /**
     * Показать popup menu при нажатии на три точки
     */
    private fun showPopupMenu(view: View, product: Product) {
        // inflate menu
        val popup = PopupMenu(mContext, view)
        val inflater = popup.getMenuInflater()
        inflater.inflate(R.menu.menu_product, popup.getMenu())
        popup.setOnMenuItemClickListener(MyMenuItemClickListener(product))
        popup.show()
    }

    /**
     * Listener для картинки с тремя точками
     */
    internal inner class MyMenuItemClickListener(private var product: Product) : PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                //Добавить в корзину
                R.id.action_add_favourite -> {
                    val userName: String = MainActivity.userName!!
                    MainActivity.database!!.cartDao().insert(CartEntity(0, product.getId(), userName, Date()))
                    Toast.makeText(mContext, "Added to shopping cart", Toast.LENGTH_SHORT).show()
                    return true
                }
                //Открыть товар
                R.id.action_play_next -> {
                    val intent = Intent(mContext, ProductActivity::class.java)
                    intent.putExtra("productId", product.getId().toString())
                    mContext.startActivity(intent)
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