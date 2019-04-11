package com.gucci.liveeventbus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gucci.library.*
import org.jetbrains.anko.button
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {
    lateinit var mEvent: LiveBus.EventObserver<Any>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEvent = object :LiveBus.EventObserver<Any>(EVENT_C){
            override fun onChange(o: Any?) {

            }

        }
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

        //注册不带生命周期的事件监听
//        register(mEvent)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        unregister(mEvent)
//    }
}
