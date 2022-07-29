package com.gupaoedu.pattern.observer.guava;

import com.google.common.eventbus.EventBus;

public class GuavaEventTest {

    public static void main(String[] args) {
        GuavaEvent guavaEvent = new GuavaEvent();

        EventBus eventBus = new EventBus();
        eventBus.register(guavaEvent);
        eventBus.post("wen");

    }

}
