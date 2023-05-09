package com.umutcansahin.filterandsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umutcansahin.filterandsearch.databinding.SearchFilterFragmentBinding

class SearchAndFilterFragment : BottomSheetDialogFragment() {

    private var _binding: SearchFilterFragmentBinding? = null
    private val binding get() = _binding!!
    private var filter = Filter()
    private val args: SearchAndFilterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filter = args.filter
        initView()
    }

    private fun initView() {
        when (filter.job) {
            Job.TEACHER -> binding.radioButtonTeacher.isChecked = true
            Job.DRIVER -> binding.radioButtonDriver.isChecked = true
            Job.DOCTOR -> binding.radioButtonDoctor.isChecked = true
            else -> binding.radioButtonAllJob.isChecked = true
        }

        when (filter.sortBy) {
            SortBy.DES -> binding.radioButtonDes.isChecked = true
            SortBy.INC -> binding.radioButtonInc.isChecked = true
        }

        if (filter.color.isNotEmpty()) {
            filter.color.forEach {
                when (it) {
                    Color.BLACK -> binding.checkBoxBlack.isChecked = true
                    Color.WHITE -> binding.checkBoxWhite.isChecked = true
                    Color.YELLOW -> binding.checkBoxYellow.isChecked = true
                    Color.RED -> binding.checkBoxRed.isChecked = true
                    Color.BLUE -> binding.checkBoxBlue.isChecked = true
                    else -> {}
                }
            }
        } else {
            with(binding) {
                checkBoxBlack.isChecked = false
                checkBoxWhite.isChecked = false
                checkBoxYellow.isChecked = false
                checkBoxRed.isChecked = false
                checkBoxBlue.isChecked = false
            }
        }

        if (filter.search.isNotEmpty()) binding.editTextSearch.setText(filter.search) else binding.editTextSearch.text.clear()

        binding.buttonApply.setOnClickListener {
            createFilter()
            setFragmentResult()
        }
        binding.buttonClear.setOnClickListener {
            filter = Filter()
            initView()
        }
    }

    private fun createFilter() {
        with(binding) {
            if (radioButtonDoctor.isChecked) {
                filter.job = Job.DOCTOR
            } else if (radioButtonDriver.isChecked) {
                filter.job = Job.DRIVER
            } else if (radioButtonTeacher.isChecked) {
                filter.job = Job.TEACHER
            } else if (radioButtonAllJob.isChecked) {
                filter.job = Job.ALL_JOB
            }

            if (radioButtonDes.isChecked) filter.sortBy = SortBy.DES else filter.sortBy = SortBy.INC


            if (checkBoxBlack.isChecked) filter.color.add(Color.BLACK) else filter.color.remove(Color.BLACK)
            if (checkBoxWhite.isChecked) filter.color.add(Color.WHITE) else filter.color.remove(Color.WHITE)
            if (checkBoxYellow.isChecked) filter.color.add(Color.YELLOW) else filter.color.remove(Color.YELLOW)
            if (checkBoxRed.isChecked) filter.color.add(Color.RED) else filter.color.remove(Color.RED)
            if (checkBoxBlue.isChecked) filter.color.add(Color.BLUE) else filter.color.remove(Color.BLUE)

            filter.search = editTextSearch.text.toString()
        }
    }

    private fun setFragmentResult() {
        setFragmentResult(
            "filter",
            bundleOf("bundle" to filter)
        )
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}