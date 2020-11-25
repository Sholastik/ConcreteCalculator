package com.app.calculator_betona.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.calculator_betona.R
import com.app.calculator_betona.activity.MainActivity
import com.app.calculator_betona.activity.OrderActivity
import com.app.calculator_betona.databinding.FragmentResultsBinding
import com.app.calculator_betona.viewmodel.ViewModel


class ResultsFragment : Fragment() {

    lateinit var binding: FragmentResultsBinding
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)

        binding.orderConcrete.setOnClickListener {
            showOrder()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!)[ViewModel::class.java]
        binding.viewModel = viewModel

        (activity as MainActivity).city.let {
            viewModel.city.value = it.anotherCityName
            viewModel.price.value = it.price
        }
    }

    private fun showOrder() {
        val intent = Intent(activity, OrderActivity::class.java)
        startActivity(intent)
    }
}
