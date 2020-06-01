package com.kttss.counter.util;

import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.Event;
import io.sentry.event.UserBuilder;


public class Logger {
    void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
    }

    /**
     * Note that the ``Sentry.init`` method must be called before the static API
     * is used, otherwise a ``NullPointerException`` will be thrown.
     */
    void logWithStaticAPI() {
        /*
         Record a breadcrumb in the current context which will be sent
         with the next event(s). By default the last 100 breadcrumbs are kept.
         */
        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder().setMessage("User made an action").build()
        );
        

        // Set the user in the current context.
        Sentry.getContext().setUser(
                new UserBuilder().setEmail("hello@sentry.io").build()
        );

        /*
         This sends a simple event to Sentry using the statically stored instance
         that was created in the ``main`` method.
         */
        Sentry.capture("This is a test");

        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry using the statically stored instance
            // that was created in the ``main`` method.
            Sentry.capture(e);
        }
    }

    public static void INFO(String message) {
        Sentry.capture(message);
    }

    public static void ERROR(String message){
        Sentry.capture(message);
    }

    public static void ERROR(Event event){
        Sentry.capture(event);
    }

    public static void ERROR(Throwable throwable){
        Sentry.capture(throwable);
    }

    public static void WARN(String message){
        Sentry.capture(message);
    }
}
