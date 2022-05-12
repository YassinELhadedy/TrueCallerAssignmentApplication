package com.example.truecallerassignmentapplication.ui.blog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.exa.nanashopper.domain.AndExpr
import com.exa.nanashopper.domain.Condition
import com.exa.nanashopper.domain.Operator
import com.exa.nanashopper.domain.Pagination
import com.example.truecallerassignmentapplication.R
import com.example.truecallerassignmentapplication.databinding.FragmentRunSimultaneouslySecondApproachBinding
import com.example.truecallerassignmentapplication.infrastructure.BlogDataSource
import com.example.truecallerassignmentapplication.ui.BaseFragment
import com.example.truecallerassignmentapplication.ui.exception.ErrorMessageFactory
import com.example.truecallerassignmentapplication.ui.util.state.Status
import com.example.truecallerassignmentapplication.ui.util.state.Status.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunSimultaneouslySecondApproachFragment :
    BaseFragment<FragmentRunSimultaneouslySecondApproachBinding>() {
    private val blogViewModel: BlogSecondApproachViewModel by viewModels()
//    private val blogViewModel: BlogViewModel by activityViewModels() //shared view model

    override val layoutRes: Int
        get() = R.layout.fragment_run_simultaneously_second_approach

    override fun setupViewModel(viewDataBinding: FragmentRunSimultaneouslySecondApproachBinding) {
        viewDataBinding.viewModel = blogViewModel
    }

    private val expr1 = AndExpr(
        Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG),
        Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        blogViewModel.fetchBlogsParallelSecondApproach(
            Pagination(
                AndExpr(
                    expr1,
                    Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG)
                )
            )
        )

        observer()
    }

    // second approach to manage status
    private fun observer() {
        blogViewModel.blogData.observe(viewLifecycleOwner) {
            when (it?.status) {
                SUCCESS -> mWaitingDialog.dismissDialog()

                ERROR -> {
                    mWaitingDialog.dismissDialog()
                    if (it.data is Throwable) {
                        Toast.makeText(
                            requireContext(),
                            ErrorMessageFactory.create(requireContext(), it.data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                LOADING -> mWaitingDialog.showDialog()

            }
        }
    }

}