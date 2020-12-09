package com.inka.restaurant.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inka.restaurant.R
import com.inka.restaurant.adapter.AdapterMenuTypes
import com.inka.restaurant.model.MenuItems
import com.inka.restaurant.model.MenuTypes
import com.inka.restaurant.other.ShoppingCart
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_first.*

class FirstScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Paper.init(this)
        //ShoppingCart.clearCart()
        menuList()
        totCartCount()
        tvCart.setOnClickListener {
            if (ShoppingCart.getShoppingCartSize() != 0) {
                intent(this)
            }else{
                toast("Please add the menu")
            }
        }
    }

    private fun menuList() {
        try {
            // menu items
            val menuItems = ArrayList<MenuItems>()
            menuItems.add(
                MenuItems(
                    1,
                    "Guac de la Costa",
                    "tortllias de mais, fruit de la passion, mango",
                    "7",
                    night = true,
                    day = true
                )
            )
            menuItems.add(
                MenuItems(
                    2,
                    "Chincharron y Cerveza",
                    "citron vert / Corona sauce",
                    "7",
                    night = true,
                    day = false
                )
            )
            menuItems.add(
                MenuItems(
                    3,
                    "Guac de la Costa",
                    "tortlias de mais, fruit de la passion, mango",
                    "7",
                    night = true,
                    day = false
                )
            )
            // menu types
            val menuTypes = ArrayList<MenuTypes>()
            menuTypes.add(MenuTypes("Starter", menuItems))
            rvHotelMenues.adapter = AdapterMenuTypes(menuTypes)
        } catch (ex: Exception) {
            ex.message
        }
    }
    fun totCartCount(){
        val count="View Cart ( ${ShoppingCart.getShoppingCartSize()} ) Items"
        tvCart.text = count
    }

    override fun onBackPressed() {
        super.onBackPressed()
            ShoppingCart.clearCart()
            finishAffinity()
    }
    private fun intent(context: Context){
        val intent = Intent(context, SecondActivity::class.java)
        startActivityForResult(intent, 2)
    }
    private fun Context.toast(info: String){
        Toast.makeText(this, info, Toast.LENGTH_LONG).show()
    }
}