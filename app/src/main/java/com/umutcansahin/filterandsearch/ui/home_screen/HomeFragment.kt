package com.umutcansahin.filterandsearch.ui.home_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.umutcansahin.filterandsearch.base.BaseFragment
import com.umutcansahin.filterandsearch.common.collectFlow
import com.umutcansahin.filterandsearch.databinding.FragmentHomeBinding
import com.umutcansahin.filterandsearch.filter.Filter
import com.umutcansahin.filterandsearch.ui.home_screen.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val adapter = UserAdapter()
    private var filter = Filter()
    private val viewModel by viewModels<HomeViewModel>()
    private val args: HomeFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilterFromNavArgs()
        observeData()
        initView()
    }

    private fun getFilterFromNavArgs() {
        args.filter?.let {
            filter = it
        }
        viewModel.filterToList(filter)
    }

    private fun observeData() {
        collectFlow(viewModel.userList) {
            adapter.submitList(it)
        }
    }

    private fun initView() {
        binding.recyclerView.adapter = adapter
        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchAndFilterFragment(
                    filter
                )
            )
        }
    }


}