package com.example.truecallerassignmentapplication.ui.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truecallerassignmentapplication.infrastructure.BlogGetApiComponent
import com.example.truecallerassignmentapplication.infrastructure.BlogsRepo
import com.example.truecallerassignmentapplication.ui.util.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

const val SPERATOR = "/**sp**/"

@HiltViewModel
class BlogViewModel @Inject constructor(private val blogsRepo: BlogsRepo) : ViewModel() {
    private val _blogData = MutableLiveData<Resource<String>?>(null)
    val blogData: LiveData<Resource<String>?> = _blogData

    private val _tc10CharacterReqAnswer = MutableLiveData<String>(null)
    val tc10CharacterReqAnswer: LiveData<String> = _tc10CharacterReqAnswer

    private val _tcEvery10CharacterReqAnswer = MutableLiveData<String>(null)
    val tcEvery10CharacterReqAnswer: LiveData<String> = _tcEvery10CharacterReqAnswer

    private val _tcWordCounterReqAnswer = MutableLiveData<String>(null)
    val tcWordCounterReqAnswer: LiveData<String> = _tcWordCounterReqAnswer

//    fun releasedDropDetailInfo(dropGetApiComponent: DropGetApiComponent) = viewModelScope.launch {
//        _releasedDropData.postValue(Resource.loading(null))
//        dropRepo.get(dropGetApiComponent)
//            .catch { e ->
//                _releasedDropData.postValue(Resource.error(e.message, e))
//            }.collect {
//                when (it) {
//                    is FHReleasedDropDetails -> {
//                        _releasedDropData.postValue(Resource.success(data = it))
//                        _fHReleasedDropDetails.postValue(it)
//                    }
//                    is FHReleasedDropStyleDetails -> {
//                        _releasedDropData.postValue(Resource.success(data = it))
//                        _fHReleasedDropStyleDetails.postValue(it)
//                    }
//                    else -> _releasedDropData.postValue(Resource.success(data = it))
//                }
//            }
//    }


    fun fetchBlogsParreller(apiComponent: BlogGetApiComponent) {
        viewModelScope.launch {
            _blogData.postValue(Resource.loading(null))
            blogsRepo.get(apiComponent).zip(
                blogsRepo.get(apiComponent)
                    .zip(blogsRepo.get(apiComponent)) { secondRequestItem, thridRequestItem ->

                        val listOfEvery10thChar = secondRequestItem.chunked(10).map {
                            it[0]
                        }

                        val occurrenceOfEveryUniqueWord =
                            thridRequestItem.toLowerCase().split(Pattern.compile("\\s+"))
                                .groupBy { it }
                                .map { "${it.key}=${it.value.size}" }.joinToString("/--/")


                        "${listOfEvery10thChar.joinToString("-")}$SPERATOR${occurrenceOfEveryUniqueWord}"

                    }) { firstRequestItem, secondAndThridItem ->


                val char10th = firstRequestItem.chunked(10).map {
                    it[0]
                }[0]

                "${char10th}$SPERATOR${secondAndThridItem}"
            }.catch { }
                .collect {
                    val result = it.split(SPERATOR)
                    _tc10CharacterReqAnswer.postValue(result[0])
                    _tcEvery10CharacterReqAnswer.postValue(result[1])
                    _tcWordCounterReqAnswer.postValue(result[2])
                }
        }
    }


}