package com.umutcansahin.filterandsearch.data

import com.umutcansahin.filterandsearch.filter.ColorType
import com.umutcansahin.filterandsearch.filter.JobType

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val jobType: JobType,
    val colorType: ColorType
)

