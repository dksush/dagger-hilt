package com.example.giphy_update.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphy_update.data.model.MovieData
import com.example.giphy_update.data.repository.SearchRepository
import com.example.giphy_update.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {


    private val _renderItems = MutableLiveData<List<MovieData>>()
    val renderItems: LiveData<List<MovieData>> get() = _renderItems

    var searchText = SingleLiveEvent<String>()
    val errorToast = SingleLiveEvent<Throwable>()


    suspend fun btnSearch() {
        searchText.value?.let { text ->
            searchRepository.getMovieList(text, 1, 10).also {
                if (it.isSuccessful) {
                    _renderItems.value = it.body()?.items
                } else {

                }
            }
        }
    }

}
