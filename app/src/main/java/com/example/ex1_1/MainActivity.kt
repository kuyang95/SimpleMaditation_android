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
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.fragment1.bubbleEmitter1
import kotlinx.android.synthetic.main.fragment2.bubbleEmitter2
import kotlinx.android.synthetic.main.fragment3.bubbleEmitter3
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
                Color.parseColor("#bcaaff"),
                Color.parseColor("#ceffaa"),
                Color.parseColor("#ffcabc")
            )



        viewpager2 = findViewById(R.id.pager)
        viewpager2.adapter = ViewPager2Adapter(this)
        manageViewPagerScrollActions(ViewPager2Adapter(this))
        val springDotsIndicator = findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator)
        springDotsIndicator.setViewPager2(viewpager2)
        //viewpager2.setPageTransformer(TransformPage(viewpager2))



    }




    // 거품제조기
    fun emitBubbles(count : Int, bubbleEmitterView: BubbleEmitterView) {
        // It will create a thread and attach it to
        // the main thread
        if (count > 0 ) {
            Handler().postDelayed({
                // Random is used to select random bubble
                // size
                val size = Random.nextInt(20, 80)
                bubbleEmitterView.emitBubble(size)


                emitBubbles(count - 1, bubbleEmitterView)
            }, Random.nextLong(10, 30))
            }
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

            //var inflater : LayoutInflater =
                //var view1 : View = inflater.inflate(R.layout.bubble, findViewById(R.id.frag1), false)

               // var view2 : View = inflater.inflate(R.layout.bubble, viewpager2, false)
                //var view3 : View = inflater.inflate(R.layout.bubble, R.id.frag3 as FrameLayout, false)
                lateinit var frag : FrameLayout



                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }

                    when (position) {
                        0 -> {
                            mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.forest)

                            frag = findViewById(R.id.frag1)
                            emitBubbles(10, bubbleEmitter1)
                           //frag.addView(view1)
                        }
                        1 -> {mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.sea)


                            frag = findViewById(R.id.frag2)
                            emitBubbles(10, bubbleEmitter2)
                           // frag.addView(view2)
                        }
                        2 ->  {mediaPlayer = MediaPlayer.create(viewpager2.context, R.raw.rain)


                            frag = findViewById(R.id.frag3)
                            emitBubbles(10, bubbleEmitter3)
                            //inflater = LayoutInflater.from(context)
                          //  frag.addView(inflater.inflate(R.layout.bubble,frag, false))
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


class TransformPage (var viewPager2 : ViewPager2): ViewPager2.PageTransformer{

    override fun transformPage(page: View, position: Float) {

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
        }
    }


}