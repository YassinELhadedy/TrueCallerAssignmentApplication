package com.example.truecallerassignmentapplication.ui.blog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.truecallerassignmentapplication.R
import com.example.truecallerassignmentapplication.databinding.FragmentRunSimultaneouslyBinding
import com.example.truecallerassignmentapplication.infrastructure.BlogDataSource
import com.example.truecallerassignmentapplication.infrastructure.BlogGetApiComponent
import com.example.truecallerassignmentapplication.ui.BaseFragment
import com.example.truecallerassignmentapplication.ui.exception.ErrorMessageFactory
import com.example.truecallerassignmentapplication.ui.util.state.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunSimultaneouslyFragment : BaseFragment<FragmentRunSimultaneouslyBinding>() {
//    private val blogViewModel: BlogViewModel by activityViewModels() // to be shared view model
    private val blogViewModel: BlogViewModel by viewModels ()

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

        observer()
    }

    // second approach to manage status
    private fun observer() {
        blogViewModel.blogData.observe(viewLifecycleOwner) {
            when (it?.status) {
                Status.SUCCESS ->{} //mWaitingDialog.dismissDialog()

                Status.ERROR -> {
                   // mWaitingDialog.dismissDialog()
                    if (it.data is Throwable) {
                        Snackbar.make(requireView(), ErrorMessageFactory.create(requireContext(), it.data), Snackbar.LENGTH_LONG).show()
                    }
                }
                Status.LOADING ->{} //mWaitingDialog.showDialog()

            }
        }
    }
}