package com.sam.rest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("{{activeMQRoute}}")
                .to("log:?level=INFO&showBody=true")
                .to("log:sample");


        from("activemq:queue:writeToMq")
                .log("Messsage read from AMQ Queue : ${body}")
                .transform(simple("Hello ${body}, Welcome to JavaOutOfBounds.com"))
                .convertBodyTo(String.class, "UTF-8");
    }
}
