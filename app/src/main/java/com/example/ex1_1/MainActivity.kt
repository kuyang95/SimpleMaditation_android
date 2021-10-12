package com.example.ex1_1

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewpager2 : ViewPager2
    val argbEvaluator: ArgbEvaluator = ArgbEvaluator()
    val context : Context = this
    lateinit var colors : IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            colors = intArrayOf(
                Color.parseColor("#bcaaff"),
                Color.parseColor("#ceffaa"),
                Color.parseColor("#ffcabc")

            )



        viewpager2 = findViewById(R.id.pager)
        viewpager2.adapter = ViewPager2Adapter(this)
        manageViewPagerScrollActions(ViewPager2Adapter(this))
        //viewpager2.setPageTransformer(TransformPage(viewpager2))

    }

    override fun onBackPressed() {
        if(viewpager2.currentItem == 0) {
            super.onBackPressed()
        }
        else{
            viewpager2.currentItem = viewpager2.currentItem -1
        }
    }

    fun manageViewPagerScrollActions(pagerAdapter: ViewPager2Adapter) {
        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {
                viewpager2.setBackgroundColor(Color.parseColor("#00ff00"))
                super.onPageSelected(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (position < pagerAdapter.itemCount - 1 && position < colors.size - 1) {
                    viewpager2.setBackgroundColor(
                        (argbEvaluator.evaluate(
                            positionOffset,
                            colors[position],
                            colors[position + 1]
                        ) as Int)
                    )
                } else
                    viewpager2.setBackgroundColor(colors[colors.size - 1])
            }
        })
    }
}

class ViewPager2Adapter(fm : FragmentActivity) : FragmentStateAdapter (fm) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        // 수정된 코드1
        if (position == 0) {
            return first_fragment()
        }
        else if (position == 1) {
            return second_fragment()
        } else {
            return third_fragment()
        }

    }
}

class first_fragment() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment1, container, false)
    }

class second_fragment() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment2, container, false)
}

class third_fragment() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment3, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}


class TransformPage (var viewPager2 : ViewPager2): ViewPager2.PageTransformer{

    override fun transformPage(page: View, position: Float) {
        /*
        val pageWidth = page.width
        val pageWidthTimesPosition = pageWidth * position
        val absPosition = abs(position)

        if (position < 0) {
            page.alpha = 1.0f - absPosition
            page.translationX = -pageWidthTimesPosition * 0.92f
            page.alpha = 1.0f - absPosition
            page.translationX = -pageWidthTimesPosition * 0.92f
        } else {
            page.alpha = 1.0f - absPosition
            page.translationX = -pageWidthTimesPosition
            page.alpha = 1.0f - absPosition
            page.translationX = -pageWidthTimesPosition
        }*/
    }


}