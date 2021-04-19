package com.example.giphy_update.ui.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.giphy_update.R
import com.example.giphy_update.databinding.FragmentMovieBinding
import com.example.giphy_update.ui.base.BaseFragment
import com.example.giphy_update.util.clicks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
//import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint //의존성을 받아 보고자 하는 안드로이드 컴포넌트에 작성해 준다.
class MovieFragment : BaseFragment<FragmentMovieBinding>(R.layout.fragment_movie) {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = this@MovieFragment
            vm = viewModel


            btnSearch.clicks(disposable = disposables) {
                lifecycleScope.launch {
                    viewModel.btnSearch()
                }
            }

            recycle.run {
                movieAdapter = MovieAdapter()
                adapter = movieAdapter
                addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            }
        }



        observeListener()


    }

    private fun observeListener() {
//        vm.blankInputText.observe(viewLifecycleOwner, Observer {
//            requireContext().toast(getString(R.string.black_input_text))
//        })
//
//
//        vm.errorToast.observe(viewLifecycleOwner, Observer {
//            requireContext().toast(it.toString())
//        })
    }

}