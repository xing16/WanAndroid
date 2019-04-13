package com.xing.commonbase.util;


import org.greenrobot.eventbus.EventBus;

public class EventBusHelper {

    private EventBusHelper() {
        throw new UnsupportedOperationException("can't be init");
    }

    public static void register(Object object) {
        EventBus.getDefault().register(object);
    }

    public static void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    public static void postSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }

}
