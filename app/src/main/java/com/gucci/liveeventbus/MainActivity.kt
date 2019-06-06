package com.gucci.liveeventbus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout
import com.gucci.library.*
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {
    lateinit var mEvent: EventObserver<Any>
    lateinit var mLl: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testView = TestView(this)
        mEvent = object :EventObserver<Any>(EVENT_C){
            override fun onChange(o: Any?) {

            }

        }
        mLl = verticalLayout {
            button("addView"){
                onClick {
                    mLl.addView(testView)
                }
            }
            button("deleteView"){
                onClick {
                    mLl.removeView(testView)
                }
            }
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
