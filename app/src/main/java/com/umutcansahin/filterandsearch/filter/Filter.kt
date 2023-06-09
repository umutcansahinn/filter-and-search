package com.umutcansahin.filterandsearch.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    var jobType: JobType = JobType.ALL_JOB,
    var sortBy: SortBy = SortBy.ASC,
    var colorTypes: List<ColorType> = listOf(
        ColorType.ALL_COLOR,
        ColorType.BLACK,
        ColorType.BLUE,
        ColorType.RED,
        ColorType.WHITE,
        ColorType.YELLOW),
    var search: String = ""
) : Parcelable
