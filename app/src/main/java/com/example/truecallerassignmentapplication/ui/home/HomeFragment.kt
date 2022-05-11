package com.example.truecallerassignmentapplication.ui.home

import com.example.truecallerassignmentapplication.R
import com.example.truecallerassignmentapplication.databinding.FragmentHomeBinding
import com.example.truecallerassignmentapplication.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun setupViewModel(viewDataBinding: FragmentHomeBinding) {
        viewDataBinding.fragment = this@HomeFragment
    }

}