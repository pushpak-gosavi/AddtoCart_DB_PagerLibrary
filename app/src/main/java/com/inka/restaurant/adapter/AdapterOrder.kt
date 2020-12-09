package com.inka.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.inka.restaurant.R
import com.inka.restaurant.activities.SecondActivity
import com.inka.restaurant.model.CartItem
import com.inka.restaurant.other.ShoppingCart
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.card_row_menu_items.view.*

class AdapterOrder(private val listOrder:ArrayList<CartItem>): RecyclerView.Adapter<AdapterOrder.MyViewHolder>() {
    private var limit:Int= 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_row_menu_items, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindProduct(listOrder[position])

    }

    override fun getItemCount(): Int {
        return if (listOrder.size > limit){
            2
        }else{
            listOrder.size
        }
        //return listOrder.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindProduct(menu: CartItem) {
            itemView.tvMenu.text = menu.menuItems.menuName
            itemView.tvmenuPrice.text = menu.menuItems.price
            Paper.init(itemView.context)
            itemView.btnAdd.visibility=View.GONE
            if (menu.menuItems.night==true){
                itemView.tvNight.visibility=View.VISIBLE
            }else{
                itemView.tvNight.visibility=View.GONE
            }
            if (menu.menuItems.day==true){
                itemView.tvDay.visibility=View.VISIBLE
            }else{
                itemView.tvDay.visibility=View.GONE
            }
            try {
                itemView.tvItemCount.text = ShoppingCart.getParicularItem(menu).toString()
            }catch (ex:Exception){
                ex.message
            }
           /* itemView.btnAdd.setOnClickListener {
                try {
                val item = CartItem(menu.menuItems)
                ShoppingCart.addItem(item)
                    (itemView.context as SecondActivity).totPrice()
                    itemView.tvItemCount.text = ShoppingCart.getParicularItem(item).toString()
                    if(ShoppingCart.getParicularItem(item)!=0){
                        itemView.btnAdd.visibility=View.GONE
                    }
            }catch (ex:Exception){
                    ex.message
                }
            }*/

            itemView.tvAdd.setOnClickListener {
                try {
                    val item = CartItem(menu.menuItems)
                    if(ShoppingCart.getParicularItem(item)!! <20) {
                        ShoppingCart.addItem(item)
                        (itemView.context as SecondActivity).totPrice()
                        itemView.tvItemCount.text = ShoppingCart.getParicularItem(item).toString()
                    }else{
                        Snackbar.make((itemView.context as SecondActivity).clSecond,
                            "${menu.menuItems.menuName} not added more than 20 ",
                            Snackbar.LENGTH_LONG).show()
                    }
                }catch (ex:Exception){
                    ex.message
                }
            }
            itemView.tvRemove.setOnClickListener {
                try {
                    val item = CartItem(menu.menuItems)
                    ShoppingCart.removeItem(item)
                    itemView.tvItemCount.text = ShoppingCart.getParicularItem(item).toString()
                   /* if (ShoppingCart.getParicularItem(item)!=0){
                        itemView.btnAdd.visibility=View.GONE
                    }else{
                        itemView.btnAdd.visibility=View.VISIBLE
                    }*/
                    if (ShoppingCart.getParicularItem(item)==0){
                        listOrder.remove(menu)
                        notifyDataSetChanged()
                        if(listOrder.size==0){
                            (itemView.context as SecondActivity).intent(itemView.context)
                        }
                    }
                    (itemView.context as SecondActivity).totPrice()
                }catch (ex:Exception){
                    ex.message
                }
            }
            // tvShow more button click
            (itemView.context as SecondActivity).tvShowMore.setOnClickListener {
                try{
                    limit= listOrder.size
                    notifyDataSetChanged()
                    (itemView.context as SecondActivity).tvShowMore.visibility=View.GONE
            }catch (ex:Exception){
                ex.message
                }
            }
        }
    }
}