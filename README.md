# 使用极为简洁的liveeventbus，后续单独建个工程支持跨进程
 
#用法
maven { url 'https://jitpack.io' }
compile 'com.github.wangdanlizhiyun:bus:1.0.0'
  
  ```
  sendEvent(EVENT_A, "aaa")
  sendStickEvent(EVENT_C, "ccc")
  registerStick<String>(EVENT_C) {
              Log.e("test", "  接受粘性c事件 o=$it")
          }
  //注册livebus监听，ondestory时自动解绑
          listener<String>(EVENT_A) {
              Log.e("test", "  主界面可见后，a事件数据变化了 o=$it")
          }
   
  ```
  
    
 