package com.gucci.liveeventbus

import android.view.View
import kotlinx.coroutines.runBlocking

/**
 * Created by 李志云 2019/1/2 17:05
 */
val mClickTimeMap = HashMap<Int, Long>()
fun View.onOneClick(action: suspend () -> Unit) {
    setOnClickListener {
        if (System.currentTimeMillis() - (mClickTimeMap.get(it.hashCode()) ?: 0) > 500) {
            runBlocking {
                mClickTimeMap[it.hashCode()] = System.currentTimeMillis()
                action()
            }
        }
    }
}