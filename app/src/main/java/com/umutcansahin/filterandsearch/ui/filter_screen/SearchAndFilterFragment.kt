package com.umutcansahin.filterandsearch.ui.filter_screen

import android.os.Bundle
import android.util.ArraySet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umutcansahin.filterandsearch.databinding.SearchFilterFragmentBinding
import com.umutcansahin.filterandsearch.filter.ColorType
import com.umutcansahin.filterandsearch.filter.Filter
import com.umutcansahin.filterandsearch.filter.JobType
import com.umutcansahin.filterandsearch.filter.SortBy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAndFilterFragment : BottomSheetDialogFragment() {

    private var _binding: SearchFilterFragmentBinding? = null
    private val binding get() = _binding!!
    private var filter = Filter()
    private val args: SearchAndFilterFragmentArgs by navArgs()

    private val selectedCheckBoxes = ArrayList<ColorType>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilterFromNavArgs()
        drawUiWithFilter()
        settingUi()
        setFilter()
    }

    private fun getFilterFromNavArgs() {
        args.filter?.let {
            filter = it
        }
    }

    private fun drawUiWithFilter() {
        when (filter.sortBy) {
            SortBy.DES -> binding.radioButtonDes.isChecked = true
            SortBy.ASC -> binding.radioButtonAsc.isChecked = true
        }

        when (filter.jobType) {
            JobType.ALL_JOB -> {
                binding.radioButtonAllJob.isChecked = true
            }
            JobType.TEACHER -> {
                binding.radioButtonTeacher.isChecked = true
            }
            JobType.DRIVER -> {
                binding.radioButtonDriver.isChecked = true
            }
            JobType.DOCTOR -> {
                binding.radioButtonDoctor.isChecked = true
            }
        }

        filter.colorTypes.forEach {
            when (it) {
                ColorType.ALL_COLOR -> {
                    binding.checkBoxAllColor.isChecked = true
                    binding.checkBoxYellow.isChecked = true
                    binding.checkBoxWhite.isChecked = true
                    binding.checkBoxRed.isChecked = true
                    binding.checkBoxBlue.isChecked = true
                    binding.checkBoxBlack.isChecked = true
                }
                ColorType.YELLOW -> binding.checkBoxYellow.isChecked = true
                ColorType.WHITE -> binding.checkBoxWhite.isChecked = true
                ColorType.RED -> binding.checkBoxRed.isChecked = true
                ColorType.BLUE -> binding.checkBoxBlue.isChecked = true
                ColorType.BLACK -> binding.checkBoxBlack.isChecked = true
            }
        }
    }

    private fun settingUi() {
        binding.checkBoxAllColor.setOnClickListener {
            val isChecked = binding.checkBoxAllColor.isChecked
            binding.checkBoxYellow.isChecked = isChecked
            binding.checkBoxWhite.isChecked = isChecked
            binding.checkBoxRed.isChecked = isChecked
            binding.checkBoxBlue.isChecked = isChecked
            binding.checkBoxBlack.isChecked = isChecked
        }
        binding.checkBoxYellow.setOnClickListener {
            binding.checkBoxAllColor.isChecked = isCheckForColor()
        }
        binding.checkBoxWhite.setOnClickListener {
            binding.checkBoxAllColor.isChecked = isCheckForColor()
        }
        binding.checkBoxRed.setOnClickListener {
            binding.checkBoxAllColor.isChecked = isCheckForColor()
        }
        binding.checkBoxBlue.setOnClickListener {
            binding.checkBoxAllColor.isChecked = isCheckForColor()
        }
        binding.checkBoxBlack.setOnClickListener {
            binding.checkBoxAllColor.isChecked = isCheckForColor()
        }
    }

    private fun isCheckForColor(): Boolean {
        val list: ArraySet<Boolean> = ArraySet()
        list.add(binding.checkBoxWhite.isChecked)
        list.add(binding.checkBoxBlue.isChecked)
        list.add(binding.checkBoxYellow.isChecked)
        list.add(binding.checkBoxRed.isChecked)
        list.add(binding.checkBoxBlack.isChecked)
        return if (list.size == 1) list.valueAt(0) else false
    }

    private fun setFilter() {
        binding.apply {
            buttonClear.setOnClickListener {
                filter = Filter()
                drawUiWithFilter()
            }
            buttonApply.setOnClickListener {
                when {
                    radioButtonDes.isChecked -> {
                        filter.sortBy = SortBy.DES
                    }
                    radioButtonAsc.isChecked -> {
                        filter.sortBy = SortBy.ASC
                    }
                }
                when {
                    radioButtonAllJob.isChecked -> {
                        filter.jobType = JobType.ALL_JOB
                    }
                    radioButtonDriver.isChecked -> {
                        filter.jobType = JobType.DRIVER
                    }
                    radioButtonTeacher.isChecked -> {
                        filter.jobType = JobType.TEACHER
                    }
                    radioButtonDoctor.isChecked -> {
                        filter.jobType = JobType.DOCTOR
                    }
                }

                if (checkBoxBlue.isChecked) {
                    selectedCheckBoxes.add(ColorType.BLUE)
                } else {
                    selectedCheckBoxes.remove(ColorType.BLUE)
                }

                if (checkBoxWhite.isChecked) {
                    selectedCheckBoxes.add(ColorType.WHITE)
                } else {
                    selectedCheckBoxes.remove(ColorType.WHITE)
                }

                if (checkBoxYellow.isChecked) {
                    selectedCheckBoxes.add(ColorType.YELLOW)
                } else {
                    selectedCheckBoxes.remove(ColorType.YELLOW)
                }

                if (checkBoxRed.isChecked) {
                    selectedCheckBoxes.add(ColorType.RED)
                } else {
                    selectedCheckBoxes.remove(ColorType.RED)
                }

                if (checkBoxBlack.isChecked) {
                    selectedCheckBoxes.add(ColorType.BLACK)
                } else {
                    selectedCheckBoxes.remove(ColorType.BLACK)
                }

                if (checkBoxAllColor.isChecked) {
                    selectedCheckBoxes.add(ColorType.ALL_COLOR)
                } else {
                    selectedCheckBoxes.remove(ColorType.ALL_COLOR)
                }

                filter.colorTypes = selectedCheckBoxes

                findNavController().navigate(
                    SearchAndFilterFragmentDirections.actionSearchAndFilterFragmentToHomeFragment(
                        filter
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}