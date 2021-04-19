package com.example.giphy_update

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.giphy_update.databinding.ActivityMainBinding
import com.example.giphy_update.ui.image.ImageFragment
import com.example.giphy_update.ui.movie.MovieFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint//의존성을 받아 보고자 하는 안드로이드 컴포넌트에 작성해 준다.
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        with(binding) {

            pager.adapter = ViewPagerAdapter(this@MainActivity)
            tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            TabLayoutMediator(
                binding.tabLayout,
                binding.pager
            ) { tab, position ->
                tab.text = when (position) {
                    0 -> "Movie"
                    1 -> "Image"
                    else -> ""
                }
            }.attach()
        }
    }


    class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val fragmentList =
            listOf(MovieFragment(), ImageFragment())

        override fun getItemCount() = fragmentList.size
        override fun createFragment(position: Int) = fragmentList[position] as Fragment
    }

}