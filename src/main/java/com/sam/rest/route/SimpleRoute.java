package com.sam.rest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://mytimer?period=100&repeatCount=5")
                .routeId("timerRoute")
                .setBody(simple("this is test msg ! ------{{message}}"))
        .to("stream:out");
    }
}
