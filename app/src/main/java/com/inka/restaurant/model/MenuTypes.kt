package com.inka.restaurant.model

data class MenuTypes(
    val menuType:String?= null,
    val menuItems: List<MenuItems>
)
data class MenuItems(
    val menuID:Int?=null,
    val menuName:String?=null,
    val menuDetails:String?=null,
    val price:String?=null,
    val night:Boolean?=null,
    val day:Boolean?=null
)