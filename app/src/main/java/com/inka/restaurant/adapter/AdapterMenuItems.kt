package com.inka.restaurant.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.inka.restaurant.R
import com.inka.restaurant.activities.FirstScreen
import com.inka.restaurant.model.CartItem
import com.inka.restaurant.model.MenuItems
import com.inka.restaurant.other.ShoppingCart
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.card_row_menu_items.view.*

class AdapterMenuItems(private val listMenuItems: List<MenuItems>) :
    RecyclerView.Adapter<AdapterMenuItems.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_row_menu_items, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            holder.bindProduct(listMenuItems[position])
        } catch (ex: Exception) {
            ex.message
        }
    }
    override fun getItemCount(): Int {
        return listMenuItems.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindProduct(menu: MenuItems) {
            itemView.tvMenu.text = menu.menuName
            itemView.tvmenuPrice.text = menu.price
            Paper.init(itemView.context)
            if (menu.night==true){
                itemView.tvNight.visibility=View.VISIBLE
            }else{
                itemView.tvNight.visibility=View.GONE
            }
            if (menu.day==true){
                itemView.tvDay.visibility=View.VISIBLE
            }else{
                itemView.tvDay.visibility=View.GONE
            }
            val item = CartItem(menu)
            try {
                itemView.tvItemCount.text = ShoppingCart.getParicularItem(item).toString()
                if (ShoppingCart.getParicularItem(item)!=0){
                    itemView.btnAdd.visibility=View.GONE
                }else{
                    itemView.btnAdd.visibility=View.VISIBLE
                }
            }catch (ex:Exception){
                ex.message
            }
            itemView.btnAdd.setOnClickListener {
                ShoppingCart.addItem(item)
                (itemView.context as FirstScreen).totCartCount()
                itemView.tvItemCount.text = ShoppingCart.getParicularItem(item).toString()
                if(ShoppingCart.getParicularItem(item)!=0){
                    itemView.btnAdd.visibility=View.GONE
                }
            }

            itemView.tvAdd.setOnClickListener {
                try {
                    if(ShoppingCart.getParicularItem(item)!! <20){
                        ShoppingCart.addItem(item)
                        (itemView.context as FirstScreen).totCartCount()
                        itemView.tvItemCount.text = ShoppingCart.getParicularItem(item).toString()
                    }else {
                        Snackbar.make((itemView.context as FirstScreen).coordinator,
                        "${menu.menuName} not added more than 20 ",
                        Snackbar.LENGTH_LONG).show()
                    }

            } catch (ex: Exception) {
                    ex.message
                }
            }
            itemView.tvRemove.setOnClickListener {
                try {
                    ShoppingCart.removeItem(item)
                    (itemView.context as FirstScreen).totCartCount()
                    itemView.tvItemCount.text = ShoppingCart.getParicularItem(item).toString()
                    if (ShoppingCart.getParicularItem(item)!=0){
                        itemView.btnAdd.visibility=View.GONE
                    }else{
                        itemView.btnAdd.visibility=View.VISIBLE
                    }
                } catch (ex: Exception) {
                    ex.message
                }
            }
        }
    }
}