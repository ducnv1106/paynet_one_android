package com.core.eventbus;


import org.greenrobot.eventbus.EventBus;

/**
 * Event bus wrapper
 * Created by NEO on 12/29/2016.
 */

public class EventBusWrapper {

  private EventBusWrapper() {

  }

  public static void register(Object subscriber) {
    if(null != EventBus.getDefault() && null != subscriber)
      EventBus.getDefault().register(subscriber);
  }

  public static void unregister(Object subscriber) {
    EventBus.getDefault().unregister(subscriber);
  }

  public static void post(Object event) {
    EventBus.getDefault().post(event);
  }
}
