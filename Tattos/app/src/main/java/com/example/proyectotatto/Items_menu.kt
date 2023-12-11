package com.example.proyectotatto

sealed class Items_menu(
    val icon: Int,
    val title: String,
    val ruta: String
){
    object Pantalla1: Items_menu(R.drawable.baseline_extension_24, "Inicio", "pantalla1")
    object Pantalla2: Items_menu(R.drawable.baseline_format_paint_24, "Contenidos", "pantalla2")
    object Pantalla3: Items_menu(R.drawable.baseline_local_shipping_24, "Carrito", "pantalla2")
}