package com.hophey.pract2.utils

import java.time.Year

fun getListOfYears(): List<String>{
    val currentYear = Year.now().value
    return (1901..currentYear).map { it.toString() }
}

fun getListOfCategories(): List<String>{
    return listOf<String>(
        "Physics",
        "Physiology or Medicine",
        "Chemistry",
        "Literature",
        "Peace",
        "Economic Sciences"
    )
}