package com.example.budayakita.ui.onboarding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.budayakita.R
import com.example.budayakita.ui.auth.register.RegisterActivity

class OnboardingActivity : AppCompatActivity() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var indicatorViews: ArrayList<View>
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        btnStart = findViewById(R.id.btnStart)

        indicatorViews = ArrayList()

        onboardingViewModel.onboardingItems.observe(this, Observer { onboardingItems ->
            val adapter = OnboardingAdapter(this, onboardingItems)
            viewPager.adapter = adapter

            setupIndicators(onboardingItems.size)

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateIndicators(position)

                    if (position == onboardingItems.size - 1) {
                        showButtonSmoothly()
                    } else {
                        hideButtonSmoothly()
                    }
                }
            })
        })

        btnStart.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupIndicators(count: Int) {
        indicatorLayout.removeAllViews()

        for (i in 0 until count) {
            val dot = View(this)
            val params = LinearLayout.LayoutParams(35, 35)
            params.setMargins(10, 0, 10, 0)
            dot.layoutParams = params
            dot.setBackgroundResource(R.drawable.indicator_inactive)

            indicatorLayout.addView(dot)
            indicatorViews.add(dot)
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in indicatorViews.indices) {
            if (i == position) {
                indicatorViews[i].setBackgroundResource(R.drawable.indicator_active)
            } else {
                indicatorViews[i].setBackgroundResource(R.drawable.indicator_inactive)
            }
        }
    }

    private fun showButtonSmoothly() {
        btnStart.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(btnStart, "alpha", 0f, 1f).apply {
            duration = 500
        }.start()
    }

    private fun hideButtonSmoothly() {
        ObjectAnimator.ofFloat(btnStart, "alpha", 1f, 0f).apply {
            duration = 500
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    btnStart.visibility = View.GONE
                }
            })
        }.start()
    }
}
