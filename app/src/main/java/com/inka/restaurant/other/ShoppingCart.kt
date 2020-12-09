package com.inka.restaurant.other

import com.inka.restaurant.model.CartItem
import io.paperdb.Paper

class ShoppingCart {
    companion object {
        fun addItem(cartItem: CartItem) {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.menuItems.menuID == cartItem.menuItems.menuID }
            if (targetItem == null) {
                cartItem.quantity++
                cart.add(cartItem)
            } else {
                targetItem.quantity++
            }
            saveCart(cart)
        }
        fun removeItem(cartItem: CartItem) {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.menuItems.menuID == cartItem.menuItems.menuID }
            if (targetItem != null) {
                if (targetItem.quantity > 0) {
                     targetItem.quantity--
                    if(targetItem.quantity==0){
                        cart.remove(targetItem)
                    }
                } else {
                    cart.remove(targetItem)
                }
            }
            saveCart(cart)
        }
        private fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }
        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())
        }
        fun getShoppingCartSize(): Int {
            var cartSize = 0
            getCart().forEach {
                cartSize += it.quantity
            }
            return cartSize
        }
        fun clearCart(){
            Paper.book().delete("cart")
        }
        fun totalPrice():Double{
            var totPrice =0.00
            val totListItems= getCart().size
            for (i in 0 until totListItems){

                totPrice += (getCart()[i].menuItems.price?.toDouble()!!) * (getCart()[i].quantity)
            }
            return totPrice
        }
        fun getParicularItem(cartItem: CartItem): Int? {
            val cart = getCart()
            val targetItem = cart.singleOrNull { it.menuItems.menuID == cartItem.menuItems.menuID }
            return targetItem?.quantity ?: 0
        }
    }
}

