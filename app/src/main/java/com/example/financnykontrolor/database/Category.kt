package com.example.financnykontrolor.database

/**
 * Category - enum class Category to category of income or expense of money
 */
enum class Category {
    SALARY, ALLOWANCE,                                  //income
    GIFTS, OTHER,                                       //income + expense
    FOOD, EDUPAGE, TRANSPORT, FAMILY, SHOPPING, HEALTH  //expense
}