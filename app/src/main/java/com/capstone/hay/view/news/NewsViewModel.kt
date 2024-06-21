package com.capstone.hay.view.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.hay.data.repository.ArticleRepository
import com.capstone.hay.data.response.ArticleResponse
import com.capstone.hay.data.response.DataItem
import com.capstone.hay.data.response.RegisterResponse
import com.capstone.hay.utils.Event
import kotlinx.coroutines.launch

class NewsViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _articleResult = MutableLiveData<Result<ArticleResponse>>()
    val articleResult: LiveData<Result<ArticleResponse>> = _articleResult

    private val _articleByIdResult = MutableLiveData<Result<DataItem>>()
    val articleByIdResult: LiveData<Result<DataItem>> = _articleByIdResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isShowTextNotFound = MutableLiveData<Boolean>()
    val isShowTextNotFound: LiveData<Boolean> = _isShowTextNotFound

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun getAllArticle() {
            viewModelScope.launch {
                _isShowTextNotFound.value = false
                _isLoading.value = true
                val result = articleRepository.getAllArticle()
                if (result.isSuccess) {
                    _articleResult.value = result
                    _isLoading.value = false
                } else {
                    _snackbarText.value = Event(result.exceptionOrNull()?.message ?: "Internal server error")
                    _isLoading.value = false
                    _isShowTextNotFound.value = true
                }
            }

    }

    fun getAllArticleByTitle(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isShowTextNotFound.value = false
            val result = articleRepository.getSearchArticle(title)
            if (result.isSuccess) {
                _articleResult.value = result
                _isLoading.value = false
            } else {
                _snackbarText.value = Event(result.exceptionOrNull()?.message ?: "Internal server error")
                _isLoading.value = false
                _isShowTextNotFound.value = true
            }
        }

    }

    fun getArticleById(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = articleRepository.getArticleById(id)
            if (result.isSuccess) {
                _articleByIdResult.value = result
                _isLoading.value = false
                Log.d("Result", result.toString())
            } else {
                _snackbarText.value = Event(result.exceptionOrNull()?.message ?: "Internal server error")
                _isLoading.value = false
            }
        }
    }
}