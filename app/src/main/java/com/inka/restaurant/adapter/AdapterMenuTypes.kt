package com.inka.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inka.restaurant.R
import com.inka.restaurant.model.MenuTypes
import kotlinx.android.synthetic.main.card_row_menu.view.*

class AdapterMenuTypes(private val menuTypes:ArrayList<MenuTypes>): RecyclerView.Adapter<AdapterMenuTypes.MyViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view:View= LayoutInflater.from(parent.context).inflate(R.layout.card_row_menu,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tvMenuType.text=menuTypes[position].menuType
        try {
                holder.itemView.rvmenuItems.apply {
                adapter= AdapterMenuItems(menuTypes[position].menuItems)
                setRecycledViewPool(viewPool)
            }
        }catch (ex:Exception){
            ex.message
        }
    }

    override fun getItemCount(): Int {
       return menuTypes.size
    }
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}