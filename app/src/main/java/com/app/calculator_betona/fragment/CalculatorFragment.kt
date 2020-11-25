package com.app.calculator_betona.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.calculator_betona.R
import com.app.calculator_betona.activity.MainActivity
import com.app.calculator_betona.databinding.FragmentCalculatorBinding
import com.app.calculator_betona.util.getCityName
import com.app.calculator_betona.util.logEvent
import com.app.calculator_betona.viewmodel.ViewModel

class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)

        binding.calculate.setOnClickListener {
            showResults()
        }
        logEvent(
            activity!!,
            "Calculator_Screen_Opened",
            getCityName(activity!!, (activity as MainActivity).getSharedPref())
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!)[ViewModel::class.java]

        binding.viewmodel = viewModel

        (activity as MainActivity).city.let {
            if (viewModel.concretePrice.value == 0.0) {
                viewModel.concretePrice.value = it.concretePrice
            }
            if (viewModel.sandPrice.value == 0.0) {
                viewModel.sandPrice.value = it.sandPrice
            }
            if (viewModel.crushedStonePrice.value == 0.0) {
                viewModel.crushedStonePrice.value = it.crushedStonePrice
            }
        }
    }

    private fun showResults() {
        activity?.let {
            (activity as MainActivity).showResults()
        }
    }
}
