package com.umutcansahin.filterandsearch.ui.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.umutcansahin.filterandsearch.data.User
import com.umutcansahin.filterandsearch.filter.ColorType
import com.umutcansahin.filterandsearch.filter.Filter
import com.umutcansahin.filterandsearch.filter.JobType
import com.umutcansahin.filterandsearch.filter.SortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList.asStateFlow()

    fun filterToList(filter: Filter) {
        filter.colorTypes.forEach {
            Log.d("colors",it.name)
        }
        val list = getList()
        val sortedList = when (filter.sortBy) {
            SortBy.DES -> list.sortedByDescending { it.id }
            SortBy.ASC -> list.sortedBy { it.id }
        }
        val jobTypeList = when(filter.jobType) {
            JobType.ALL_JOB->{sortedList}
            JobType.TEACHER-> {sortedList.filter { it.jobType == JobType.TEACHER }}
            JobType.DRIVER-> {sortedList.filter { it.jobType == JobType.DRIVER }}
            JobType.DOCTOR-> {sortedList.filter { it.jobType == JobType.DOCTOR }}
        }
        val colorList = jobTypeList.filter {
            filter.colorTypes.contains(it.colorType)
        }
        _userList.value = colorList
    }

    private fun getList(): ArrayList<User> {
        val userList = ArrayList<User>()
        userList.add(User(1, "umut", "umut@gmail.com", JobType.DOCTOR, ColorType.BLACK))
        userList.add(User(2, "ali", "ali@gmail.com", JobType.DOCTOR, ColorType.WHITE))
        userList.add(User(3, "mehmet", "mehmet@gmail.com", JobType.DRIVER, ColorType.RED))
        userList.add(User(4, "hasan", "hasan@gmail.com", JobType.DRIVER, ColorType.YELLOW))
        userList.add(User(5, "halil", "halil@gmail.com", JobType.TEACHER, ColorType.BLUE))
        userList.add(User(6, "mustafa", "mustafa@gmail.com", JobType.TEACHER, ColorType.BLACK))
        userList.add(User(7, "melih", "melih@gmail.com", JobType.TEACHER, ColorType.RED))
        return userList
    }
}