package ies.ua.inouttracker.ui.model

data class User(
    val id: Int,
    val type: Int,
    val name: String,
    val username: String,
    val password: String,
)