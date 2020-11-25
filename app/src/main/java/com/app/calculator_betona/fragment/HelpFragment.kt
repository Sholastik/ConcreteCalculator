package com.app.calculator_betona.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.calculator_betona.R
import com.app.calculator_betona.activity.MainActivity
import com.app.calculator_betona.databinding.FragmentHelpBinding
import com.app.calculator_betona.util.getCityName
import com.app.calculator_betona.util.logEvent

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false)

        logEvent(
            activity!!,
            "Help_Screen_Opened",
            getCityName(activity!!, (activity as MainActivity).getSharedPref())
        )

        return binding.root
    }

}
