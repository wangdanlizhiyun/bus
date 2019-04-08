package com.gucci.liveeventbus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gucci.library.LiveBus
import com.gucci.library.listener
import com.gucci.library.sendEvent
import com.gucci.library.sendStickEvent
import org.jetbrains.anko.button
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            button("进入第二个页面") {
                onOneClick {
                    startActivity<SecondActivity>()
                }
            }
            button("发送a事件") {
                onOneClick {
                    sendEvent(EVENT_A, "aaa")
                }
            }
            button("子线程发送a事件") {
                onOneClick {
                    Thread() {
                        kotlin.run {
                            sendEvent(EVENT_A, "aaa")
                        }
                    }.start()

                }
            }
            button("发送粘性c事件") {
                onOneClick {
                    sendStickEvent(EVENT_C, "ccc")
                }
            }

        }
        //注册livebus监听，ondestory时自动解绑
        listener<String>(EVENT_A) {
            Log.e("test", "  主界面可见后，a事件数据变化了 o=$it")
        }
    }
}
