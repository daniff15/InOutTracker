package ies.ua.inouttracker.ui.model

data class Store(
    val id: Int,
    val shop_id: Int,
    val name: String,
    val opening_time: String,
    val closing_time: String,
    val max_capacity: Int,
    val people_count: Int,
    val waiting: Int
) {
}