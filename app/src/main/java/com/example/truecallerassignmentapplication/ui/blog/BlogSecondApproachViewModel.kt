package com.example.truecallerassignmentapplication.ui.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exa.nanashopper.domain.Pagination
import com.example.truecallerassignmentapplication.domain.service.Blogs
import com.example.truecallerassignmentapplication.ui.util.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BlogSecondApproachViewModel @Inject constructor(private val blogService: Blogs) :
    ViewModel() {
    private val _blogData = MutableLiveData<Resource<String>?>(null)
    val blogData: LiveData<Resource<String>?> = _blogData

    private val _tcLoader = MutableLiveData<Boolean>(false)
    val tcLoader: LiveData<Boolean> = _tcLoader

    private val _tc10CharacterReqAnswer = MutableLiveData<String>(null)
    val tc10CharacterReqAnswer: LiveData<String> = _tc10CharacterReqAnswer

    private val _tcEvery10CharacterReqAnswer = MutableLiveData<String>(null)
    val tcEvery10CharacterReqAnswer: LiveData<String> = _tcEvery10CharacterReqAnswer

    private val _tcWordCounterReqAnswer = MutableLiveData<String>(null)
    val tcWordCounterReqAnswer: LiveData<String> = _tcWordCounterReqAnswer

    fun fetchBlogsParallel(pagination: Pagination) {
        viewModelScope.launch {
            _blogData.postValue(Resource.loading(null))
            _tcLoader.postValue(true)
            blogService.getBlogs(pagination)
                .catch { e ->
                    _tcLoader.postValue(false)
                    Log.i("dsadsa", e.message!!)
                }
                .collect {
                    _tcLoader.postValue(false)
                    val result = it.split(SEPARATOR)
                    _tc10CharacterReqAnswer.postValue(result[0])
                    _tcEvery10CharacterReqAnswer.postValue(result[1])
                    _tcWordCounterReqAnswer.postValue(result[2])
                }
        }
    }


}