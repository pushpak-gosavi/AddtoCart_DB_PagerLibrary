package com.inka.restaurant.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inka.restaurant.R
import com.inka.restaurant.adapter.AdapterOrder
import com.inka.restaurant.model.CartItem
import com.inka.restaurant.other.ShoppingCart
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Paper.init(this)
        try {
            totPrice()
            rvOrder.adapter=AdapterOrder(ShoppingCart.getCart() as ArrayList<CartItem>)
            showMore()
            btnPlaceOrder.setOnClickListener {
                val id: Int = rgOrderType.checkedRadioButtonId
                if (id!=-1){
                    ShoppingCart.clearCart()
                    toast("Order Place successfully")
                    intent(this)
                }else{
                    toast("Please Select the Delivery Option")
                }
            }
            ibBack.setOnClickListener {
                intent(this)
            }
        }catch (ex: Exception){
            ex.message
        }
        rgOrderType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbDineIn) {
                toast("Dinner in ")
            } else if (checkedId == R.id.rbTakeWay) {
                toast("Order is take way")
            }

        }
    }
    fun totPrice(){
        tvToalPrice.text = (String.format("%.2f", ShoppingCart.totalPrice()).toDouble()).toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
       intent(this@SecondActivity)
    }
    private fun showMore(){
        if(ShoppingCart.getCart().size <= 2){
            tvShowMore.visibility= View.GONE
        }else{
            tvShowMore.visibility=View.VISIBLE
        }
    }
    fun intent(context: Context){
        val intent= Intent(context, FirstScreen::class.java)
        startActivity(intent)
        finish()
        /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)*/
    }
       private fun Context.toast(info: String){
        Toast.makeText(this, info, Toast.LENGTH_LONG).show()
    }
}