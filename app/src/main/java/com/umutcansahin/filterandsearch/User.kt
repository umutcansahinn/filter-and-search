package com.umutcansahin.filterandsearch

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val job: Job,
    val color: Color
)

enum class Job {
    DOCTOR,
    DRIVER,
    TEACHER,
    ALL_JOB
}

enum class SortBy {
    DES,
    INC
}

enum class Color {
    WHITE,
    BLACK,
    YELLOW,
    RED,
    BLUE,
}

@Parcelize
data class Filter(
    var job: Job = Job.ALL_JOB,
    var sortBy: SortBy = SortBy.DES,
    var color: ArrayList<Color> = arrayListOf(),
    var search: String = ""
) : Parcelable

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

