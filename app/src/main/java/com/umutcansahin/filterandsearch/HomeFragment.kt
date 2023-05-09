package com.umutcansahin.filterandsearch

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.umutcansahin.filterandsearch.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter = Adapter()
    private var filter = Filter()

    private var userList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userList.add(User(1, "umut", "umut@gmail.com", Job.DOCTOR, Color.BLACK))
        userList.add(User(2, "ali", "ali@gmail.com", Job.DOCTOR, Color.WHITE))
        userList.add(User(3, "mehmet", "mehmet@gmail.com", Job.DRIVER, Color.RED))
        userList.add(User(4, "hasan", "hasan@gmail.com", Job.DRIVER, Color.YELLOW))
        userList.add(User(5, "halil", "halil@gmail.com", Job.TEACHER, Color.BLUE))
        userList.add(User(6, "mustafa", "mustafa@gmail.com", Job.TEACHER, Color.BLACK))
        userList.add(User(7, "melih", "melih@gmail.com", Job.TEACHER, Color.RED))

        initView()
        fragmentResultListener()

    }

    private fun initView() {
        binding.recyclerView.adapter = adapter

        setListToAdapter()

        binding.buttonFilter.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToSearchAndFilterFragment(filter = filter)
            findNavController().navigate(action)
        }

    }

    private fun setListToAdapter() {
        adapter.userList = when {
            filter.job != Job.ALL_JOB -> userList.filter { it.job == filter.job }
            else -> userList
        }.run {
            when (filter.sortBy.name) {
                SortBy.DES.name -> this
                else -> this.reversed()
            }
        }.run {
            when {
                filter.color.isNotEmpty() -> this.filter { filter.color.contains(it.color) }
                else -> this
            }
        }.run {
            this.filter {user->
                user.name.contains(filter.search)
            }
        }

/*
        adapter.userList = if (filter.job != Job.ALL_JOB) {
            userList.filter { user ->
                user.job == filter.job
            }
        } else {
            userList
        }.run {
            if (filter.sortBy.name == SortBy.DES.name) {
                this
            } else {
                this.reversed()
            }
        }.run {
            if (filter.color.isNotEmpty()) {
                this.filter { user ->
                    filter.color.contains(user.color)
                }
            } else {
                this
            }
        }
*/

    }

    private fun fragmentResultListener() {
       /* setFragmentResultListener("filter") { _, bundle ->
            filter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("bundle", Filter::class.java) ?: Filter()
            } else {
                @Suppress("DEPRECATION") (bundle.getParcelable("bundle") ?: Filter())
            }
            setListToAdapter()
        }*/
        setFragmentResultListener("filter") {_,bundle->
            bundle.parcelable<Filter>("bundle")?.let {
                filter = it
            }
            setListToAdapter()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}