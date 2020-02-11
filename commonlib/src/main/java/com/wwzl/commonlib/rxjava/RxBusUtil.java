package com.wwzl.commonlib.rxjava;

import com.hwangjr.rxbus.RxBus;

/**
 * @author: weibin.chen
 * @time: 2020/1/6
 * @des : 模仿Evbus 更轻量
 */
public class RxBusUtil {
    /*
     *  订阅接收方法  自定义方法加上   @Subscribe  注解
     */
    public static  void register(Object oc){
        RxBus.get().register(oc);
    }

    public static  void unregister(Object oc){
        RxBus.get().unregister(oc);
    }

    public static  void post(Object oc){
        RxBus.get().post(oc);
    }
    public static  void post(String tag,Object oc){
        RxBus.get().post(tag,oc);
    }

}
