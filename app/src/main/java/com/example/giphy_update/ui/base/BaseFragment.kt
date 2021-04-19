package com.example.giphy_update.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

open class BaseFragment<B : ViewDataBinding>(private val resId: Int) : Fragment(), KoinComponent {

    protected val disposables by lazy { CompositeDisposable() }

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, resId, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        //disposables.clear()
        disposables.dispose()
    }

    open fun initEvent() {

    }




    override fun onPause() {
        super.onPause()
        //hideKeyboard(context)
    }
}