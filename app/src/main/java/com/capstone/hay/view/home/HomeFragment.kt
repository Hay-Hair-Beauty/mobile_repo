package com.capstone.hay.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.capstone.hay.R
import com.capstone.hay.adapter.CarouselAdapter
import com.capstone.hay.data.model.CarouselItem
import com.capstone.hay.databinding.FragmentHomeBinding
import com.capstone.hay.view.ViewModelFactory
import com.capstone.hay.view.login.LoginActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager2: ViewPager2
    private lateinit var pageChangeListener: OnPageChangeCallback
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val delayTime: Long = 3000
    private var currentPage = 0
    private var isAutoPlay = true

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }
    private val viewModel by viewModels<HomeViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                binding.userName.text = resources.getString(R.string.present_user, user.name)
            }
        }

        viewPager2 = binding.viewPager
        val slideDotLL = binding.slideDotLL

        val items = listOf(
            CarouselItem(R.drawable.first_carousel_bg, R.drawable.first_carousel_object, "Genjala Kerusakan Rambut"),
            CarouselItem(R.drawable.second_carousel_bg, R.drawable.second_carousel_object, "Rambut sehat dan berkilau"),
            CarouselItem(R.drawable.third_carousel_bg, R.drawable.third_carousel_object, "Rawatlah rambut anda sekarang juga")
        )

        val adapter = CarouselAdapter(items)
        viewPager2.adapter = adapter

        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val dotImage = Array(items.size) { ImageView(requireContext())}

        dotImage.forEach {
            it.setImageResource(
                R.drawable.ic_indicator
            )
            slideDotLL.addView(it, params)
        }

        dotImage[0].setImageResource(R.drawable.ic_indicator_active)

        pageChangeListener = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(
                            R.drawable.ic_indicator_active
                        )
                    } else {
                        imageView.setImageResource(R.drawable.ic_indicator)
                    }
                }
                super.onPageSelected(position)
            }
        }

        viewPager2.registerOnPageChangeCallback(pageChangeListener)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (isAutoPlay) {
                currentPage = if (currentPage == items.size - 1) 0 else currentPage + 1
                viewPager2.setCurrentItem(currentPage, true)
            }
        }

        startAutoPlay()

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    stopAutoPlay()
                    isAutoPlay = false
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    isAutoPlay = true
                    startAutoPlay()
                }
            }
        })
    }

    private fun startAutoPlay() {
        isAutoPlay = true
        handler.postDelayed(runnable, delayTime)
    }

    private fun stopAutoPlay() {
        isAutoPlay = false
        handler.removeCallbacks(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
        stopAutoPlay()
    }
}