package com.example.truecallerassignmentapplication.ui.home

import androidx.fragment.app.activityViewModels
import com.example.truecallerassignmentapplication.R
import com.example.truecallerassignmentapplication.databinding.FragmentHomeBinding
import com.example.truecallerassignmentapplication.ui.BaseFragment
import com.example.truecallerassignmentapplication.ui.MainViewModel
import com.example.truecallerassignmentapplication.ui.NavigationJourney
import com.example.truecallerassignmentapplication.ui.Navigator
import com.fashhub.camxviewmlapplication.ui.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val mainViewModel: MainViewModel by activityViewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun setupViewModel(viewDataBinding: FragmentHomeBinding) {
        viewDataBinding.viewModel = mainViewModel
        viewDataBinding.fragment = this@HomeFragment
    }

}