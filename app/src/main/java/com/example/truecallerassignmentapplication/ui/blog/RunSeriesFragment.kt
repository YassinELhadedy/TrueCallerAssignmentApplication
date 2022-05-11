package com.example.truecallerassignmentapplication.ui.blog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.exa.nanashopper.domain.AndExpr
import com.exa.nanashopper.domain.Condition
import com.exa.nanashopper.domain.Operator
import com.exa.nanashopper.domain.Pagination
import com.example.truecallerassignmentapplication.R
import com.example.truecallerassignmentapplication.databinding.FragmentRunSeriesBinding
import com.example.truecallerassignmentapplication.infrastructure.BlogDataSource
import com.example.truecallerassignmentapplication.ui.BaseFragment

class RunSeriesFragment : BaseFragment<FragmentRunSeriesBinding>() {
    private val blogSecondApproachViewModel: BlogSecondApproachViewModel by activityViewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_run_series

    override fun setupViewModel(viewDataBinding: FragmentRunSeriesBinding) {
        viewDataBinding.viewModel = blogSecondApproachViewModel
    }

    private val expr1 = AndExpr(Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG), Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        blogSecondApproachViewModel.fetchBlogsParallel(
           Pagination(AndExpr(expr1,Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG)))
        )
    }

}