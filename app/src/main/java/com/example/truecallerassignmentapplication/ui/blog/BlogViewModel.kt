package com.example.truecallerassignmentapplication.ui.blog

import android.util.Log
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
import javax.inject.Inject

const val SEPARATOR = "/**sp**/"
const val SPACE_SEPARATOR = "\\s"
const val SEPARATOR_DECORATOR = "/--/"

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


    fun fetchBlogsParallel(apiComponent: BlogGetApiComponent) {
        viewModelScope.launch {
            _blogData.postValue(Resource.loading(null))
            blogsRepo.get(apiComponent).zip(
                blogsRepo.get(apiComponent)
                    .zip(blogsRepo.get(apiComponent)) { secondRequestItem, thirdRequestItem ->

                        val listOfEvery10thChar = secondRequestItem.chunked(10).mapNotNull {
                            if (it.length == 10) {
                                it[9]
                            } else {
                                null
                            }
                        }

                        val occurrenceOfEveryUniqueWord =
                            thirdRequestItem.toLowerCase().split(SPACE_SEPARATOR.toRegex())
                                .groupBy { it }
                                .map { "${it.key}=${it.value.size}" }.joinToString(SEPARATOR_DECORATOR)


                        "${listOfEvery10thChar.joinToString("-")}$SEPARATOR${occurrenceOfEveryUniqueWord}"

                    }) { firstRequestItem, secondAndThirdItem ->


                val char10th = firstRequestItem.chunked(10).mapNotNull {
                    if (it.length == 10) {
                        it[9]
                    } else {
                        null
                    }
                }[0]

                "${char10th}$SEPARATOR${secondAndThirdItem}"
            }.catch { e ->
                Log.i("dsadsa", e.message!!)
            }
                .collect {
                    val result = it.split(SEPARATOR)
                    _tc10CharacterReqAnswer.postValue(result[0])
                    _tcEvery10CharacterReqAnswer.postValue(result[1])
                    _tcWordCounterReqAnswer.postValue(result[2])
                }
        }
    }


}