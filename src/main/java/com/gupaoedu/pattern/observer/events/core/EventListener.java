package com.gupaoedu.pattern.observer.events.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听器，他就是观察者
 */
public class EventListener {

    /** JDK底层的Listener */
    protected Map<String, Event> eventMap = new HashMap<>();

    public void addListener(String eventTye, Object target) {
        try {
            this.addListener(eventTye,
                    target,
                    target.getClass().getMethod("on" + toUpperFirstCase(eventTye), Event.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册事件
     * @param eventType 事件类型
     * @param target 通知对象
     * @param callback 回调
     */
    public void addListener(String eventType, Object target, Method callback) {
        eventMap.put(eventType, new Event(target, callback));
    }

    /**
     * 触发事件
     * @param event 事件
     */
    private void trigger(Event event) {
        event.setSource(this);
        event.setTime(System.currentTimeMillis());
        try {
            //触发回调
            if (event.getCallback() != null) {
                event.getCallback().invoke(event.getTarget(), event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 事件名称触发
     * @param trigger 时间名称
     */
    protected void trigger(String trigger) {
        if (!this.eventMap.containsKey(trigger)) {
            return;
        }
        trigger(this.eventMap.get(trigger).setTrigger(trigger));
    }

    /**
     * 首字符大写
     * @param eventTye 字符串
     * @return 大写后的结果
     */
    private String toUpperFirstCase(String eventTye) {
        char[] chars = eventTye.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }

}
