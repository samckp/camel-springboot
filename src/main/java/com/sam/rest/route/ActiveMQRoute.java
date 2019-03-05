package com.sam.rest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("{{activeMQRoute}}")
                .to("log:?level=INFO&showBody=true")
                .to("activemq:queue:writeToMq");


        from("activemq:queue:writeToMq")
                .log("\n>>>>>>>>>>>>>>Messsage reading from AMQ Queue :\n ${body} \n<<<<<<<<<<<<<<<<<<<<<")
                .transform(simple("Testing---\n ${body}, \nWelcome to ActiveMQ POC !!"))
                .convertBodyTo(String.class, "UTF-8")
        .log("\n>>>>>>>>>>>>>>>>>>>>\n${body}<<<<<<<<<<<<<<<<<<\n");
    }
}
