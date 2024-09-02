package com.myothiha.composegraphql.domain

/**
 * @Author Liam
 * Created at 01/Sep/2024
 */
data class Ship(
    val active: Boolean = false,
    val image: String = "",
    val id: String = "",
    val name: String = "",
    val model: String? = null
)
