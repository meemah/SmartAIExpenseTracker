package com.example.smartaiexpensetracker.core.data

data class Transaction(
    val id: String,
    val category: Category,
    val title: String,
    val date: String,
    val amount: Double
)

object FakeTransactions {

    private val titles = mapOf(
        "Food" to listOf("Jollof Rice", "Lunch at KFC", "Grocery Store", "Pizza Order"),
        "Transport" to listOf("Uber Ride", "Bus Fare", "Fuel Top-up", "Bolt Trip"),
        "Rent" to listOf("Monthly Rent", "Apartment Payment"),
        "Utilities" to listOf("Electricity Bill", "Internet Subscription", "Water Bill"),
        "Shopping" to listOf("Clothes", "Shoes", "Amazon Order", "Phone Accessories"),
        "Health" to listOf("Pharmacy", "Doctor Visit", "Lab Test"),
        "Entertainment" to listOf("Netflix", "Cinema Ticket", "Concert", "Spotify"),
        "Other" to listOf("Gift", "Donation", "Misc Expense")
    )

    private val categories = CategoryData.homeCategories

    fun generate(): List<Transaction> {
        val random = java.util.Random()

        return List(30) { index ->
            val category = categories.random()
            val title = titles[category.title]?.random() ?: "Unknown"

            Transaction(
                id = "TXN-$index",
                category = category,
                title = title,
                date = randomDate(),
                amount = randomAmount(category.title)
            )
        }
    }

    private fun randomAmount(category: String): Double {
        return when (category) {
            "Rent" -> (800..1500).random().toDouble()
            "Utilities" -> (20..200).random().toDouble()
            "Transport" -> (5..50).random().toDouble()
            "Food" -> (10..100).random().toDouble()
            "Shopping" -> (15..300).random().toDouble()
            "Health" -> (20..250).random().toDouble()
            "Entertainment" -> (10..120).random().toDouble()
            else -> (5..100).random().toDouble()
        }
    }

    private fun randomDate(): String {
        val day = (1..28).random()
        val month = (1..12).random()
        val year = 2026

        return String.format("%02d-%02d-%d", day, month, year)
    }
}