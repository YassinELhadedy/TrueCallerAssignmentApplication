package com.example.truecallerassignmentapplication.ui.blog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.truecallerassignmentapplication.R
import com.example.truecallerassignmentapplication.databinding.FragmentRunSimultaneouslyBinding
import com.example.truecallerassignmentapplication.infrastructure.BlogDataSource
import com.example.truecallerassignmentapplication.infrastructure.BlogGetApiComponent
import com.example.truecallerassignmentapplication.ui.BaseFragment

class RunSimultaneouslyFragment : BaseFragment<FragmentRunSimultaneouslyBinding>() {
    private val blogViewModel: BlogViewModel by activityViewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_run_simultaneously

    override fun setupViewModel(viewDataBinding: FragmentRunSimultaneouslyBinding) {
        viewDataBinding.viewModel = blogViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        blogViewModel.fetchBlogsParallel(
            BlogGetApiComponent(
                BlogDataSource.GET_TRUE_CALLER_BLOG,
                null
            )
        )
    }
}