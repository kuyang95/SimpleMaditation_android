package com.example.ex1_1

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment1.view.*
import org.firezenk.bubbleemitter.BubbleEmitterView
import kotlin.math.abs
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var viewpager2 : ViewPager2
    val argbEvaluator: ArgbEvaluator = ArgbEvaluator()
    val context : Context = this


    lateinit var colors : IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            colors = intArrayOf(
                Color.parseColor("#83a58e"),
                Color.parseColor("#9ec3c3"),
                Color.parseColor("#7c91a6"),
                Color.parseColor("#a67e7c"),
                Color.parseColor("#8f8f8f")
            )



        viewpager2 = findViewById(R.id.pager)
        viewpager2.adapter = ViewPager2Adapter(this)
        manageViewPagerScrollActions(ViewPager2Adapter(this))
        val wormDotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        wormDotsIndicator.setViewPager2(viewpager2)
        viewpager2.setPageTransformer(TransformPage(viewpager2))
        emitBubbles()



    }




    // 거품제조기
    fun emitBubbles() {
        // It will create a thread and attach it to
        // the main thread

            Handler().postDelayed({
                // Random is used to select random bubble
                // size
                val size = Random.nextInt(20, 80)
                bubbleEmitter.canExplode(true)
                bubbleEmitter.emitBubble(size)


                emitBubbles()
            }, Random.nextLong(500, 800))

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

            var mediaPlayer : MediaPlayer = MediaPlayer()



            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }

                    when (position) {
                        0 -> {mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.forest)
                        }

                        1 -> {mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.sea2)
                        }

                        2 ->  {mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.rain)
                        }

                        3 ->  {mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.campfire2)
                        }

                        4 ->  {mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.piano)
                        }
                }




                mediaPlayer.start()

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
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return first_fragment()
            1 -> return second_fragment()
            2 -> return third_fragment()
            3 -> return fourth_fragment()
            else -> return fifth_fragment()
        }
    }
}

class first_fragment() : Fragment(){

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment1, container, false)

    // Fragment 에서 context 얻기 위함
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }
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

class fourth_fragment() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment4, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

class fifth_fragment() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment5, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}


class TransformPage (var viewPager2 : ViewPager2): ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {

        val pageWidth = page.width
        val pageWidthTimesPosition = pageWidth * position
        val absPosition = abs(position)

        if (position < 0) {
            page.title.alpha = 1.0f - absPosition
            page.title.translationX = -pageWidthTimesPosition * 0.92f

        } else {
            page.title.alpha = 1.0f - absPosition
            page.title.translationX = -pageWidthTimesPosition

        }
    }
}
