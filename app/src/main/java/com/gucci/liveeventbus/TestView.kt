package com.gucci.liveeventbus

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.gucci.library.listener
import com.gucci.lifecycle.LifecycleListener
import com.gucci.lifecycle.annotations.OnAttachedToWindow
import com.gucci.lifecycle.annotations.OnDetachedToWindow
import com.gucci.lifecycle.annotations.OnPause
import com.gucci.lifecycle.annotations.OnResume
import com.gucci.lifecycle.bind

/**
 * Created by 李志云 2019/6/4 00:50
 */
class TestView: TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

        text = "testView"
        listener<String>(EVENT_A) {
            Log.e("test", "  在TestView中监听，a事件数据变化了 o=$it")
        }

    }
}