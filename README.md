# 使用极为简洁的liveeventbus，后续单独建个工程支持跨进程
 
#用法
maven { url 'https://jitpack.io' }
compile 'com.github.wangdanlizhiyun:bus:1.1.1'
  
  ```
  //注册livebus监听，ondestory时自动解绑
    listener<String>(EVENT_A) {
        Log.e("test", "  主界面可见后，a事件数据变化了 o=$it")
    }
          
          //注册和解绑不带生命周期的事件监听
          register(mEvent)
          unregister(mEvent)
   
   //发射事件
   sendEvent(EVENT_A, "aaa")
   //发射粘性事件
   sendStickEvent(EVENT_C, "ccc")
   //注册接受粘性事件监听
   registerStick<String>(EVENT_C) {
       Log.e("test", "  接受粘性c事件 o=$it")
    }
  ```
  
    
 