package com.example.truecallerassignmentapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.truecallerassignmentapplication.ui.util.progress.WaitingDialog

abstract class BaseFragment<T:ViewDataBinding>: Fragment() {
    protected val mWaitingDialog: WaitingDialog by lazy { WaitingDialog(requireActivity()) }
    protected lateinit var viewDataBinding: T
    @get:LayoutRes
    protected abstract val layoutRes: Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate<T>(inflater, layoutRes, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            setupViewModel(this)
        }
        setupToolBarAndListener()
        return viewDataBinding.root
    }
    protected abstract fun setupViewModel(viewDataBinding: T)
    open fun setupToolBarAndListener() {}


}