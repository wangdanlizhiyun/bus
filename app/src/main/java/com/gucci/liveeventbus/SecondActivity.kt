package com.gucci.liveeventbus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gucci.library.registerStick
import com.gucci.library.sendEvent
import org.jetbrains.anko.button
import org.jetbrains.anko.verticalLayout

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            button("发送a事件") {
                onOneClick {
                    sendEvent(EVENT_A, "aaa")
                }
            }
        }
        registerStick<String>(EVENT_C) {
            Log.e("test", "  接受粘性c事件 o=$it")
        }
    }
}
