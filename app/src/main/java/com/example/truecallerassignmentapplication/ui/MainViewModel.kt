package com.example.truecallerassignmentapplication.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fashhub.camxviewmlapplication.ui.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

enum class NavigationJourney(val value: Int) {
    NOTHING(-1),
    RUN_SIMULTANEOUSLY(0),
    RUN_SIMULTANEOUSLY_SECOND_APPROACH(1);

    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}

class Navigator(val navigationJourney: NavigationJourney, val bundle: Bundle? = null)

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val _navigationJourneyLiveData = MutableLiveData<Event<Navigator>>(null)
    val navigationJourneyLiveData:LiveData<Event<Navigator>> =_navigationJourneyLiveData

    private val _navigationJourneySecondApproach = MutableStateFlow(Navigator(NavigationJourney.NOTHING))
    val navigationJourneySecondApproach:StateFlow<Navigator> =_navigationJourneySecondApproach

    fun postJourney(navigator:Navigator){
        _navigationJourneyLiveData.postValue(Event(navigator))
        _navigationJourneySecondApproach.value = navigator
    }
}