package com.example.truecallerassignmentapplication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.truecallerassignmentapplication.databinding.ActivityMainBinding
import com.example.truecallerassignmentapplication.ui.MainViewModel
import com.example.truecallerassignmentapplication.ui.NavigationJourney
import com.example.truecallerassignmentapplication.ui.NavigationJourney.*
import com.example.truecallerassignmentapplication.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
    }

    private fun observer() {
        mainViewModel.navigationJourneyLiveData.observe(this) {
            it?.let {
                when (it.peekContent().navigationJourney) {
                    NavigationJourney.RUN_SIMULTANEOUSLY -> openFragment(
                        HomeFragmentDirections.actionHomeFragmentToRunSimultaneouslyFragment()
                    )
                    NavigationJourney.RUN_SIMULTANEOUSLY_SECOND_APPROACH -> openFragment(
                        HomeFragmentDirections.actionHomeFragmentToRunSimultaneouslySecondApproachFragment()
                    )
                    else -> TODO()
                }
            }
        }

        //second approach with state flow
//        lifecycleScope.launchWhenStarted {
//            mainViewModel.navigationJourneySecondApproach.collect {
//                when(it.navigationJourney){
//                    RUN_SIMULTANEOUSLY -> openFragment(
//                        HomeFragmentDirections.actionHomeFragmentToRunSimultaneouslyFragment()
//                    )
//                    RUN_SIMULTANEOUSLY_SECOND_APPROACH -> openFragment(
//                        HomeFragmentDirections.actionHomeFragmentToRunSimultaneouslySecondApproachFragment()
//                    )
//
//                }
//            }
//        }
    }

    private fun openFragment(navigationDirection: NavDirections) {
        navigationDirection.apply {
            Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment).navigate(this)
        }
    }
}