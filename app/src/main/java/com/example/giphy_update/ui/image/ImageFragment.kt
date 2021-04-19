package com.example.giphy_update.ui.image

import android.os.Bundle
import android.view.View
import com.example.giphy_update.R
import com.example.giphy_update.databinding.FragmentImageBinding
import com.example.giphy_update.ui.base.BaseFragment

class ImageFragment : BaseFragment<FragmentImageBinding>(R.layout.fragment_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            lifecycleOwner = this@ImageFragment
        }
    }
}