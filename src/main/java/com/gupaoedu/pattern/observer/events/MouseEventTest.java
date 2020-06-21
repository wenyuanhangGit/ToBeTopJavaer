package com.gupaoedu.pattern.observer.events;

import com.gupaoedu.pattern.observer.events.mouseevent.Mouse;
import com.gupaoedu.pattern.observer.events.mouseevent.MouseEventCallback;
import com.gupaoedu.pattern.observer.events.mouseevent.MouseEventTye;

public class MouseEventTest {

    public static void main(String[] args) {
        MouseEventCallback callback = new MouseEventCallback();

        Mouse mouse = new Mouse();

        mouse.addListener(MouseEventTye.ON_CLICK, callback);
        mouse.addListener(MouseEventTye.ON_FOCUS, callback);

        mouse.click();

        mouse.focus();

    }

}
