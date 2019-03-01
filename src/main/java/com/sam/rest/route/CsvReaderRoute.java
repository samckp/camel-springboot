package com.sam.rest.route;

import com.sam.rest.dao.Item;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

 @Component
public class CsvReaderRoute extends RouteBuilder {

    @Autowired
    Environment env;

    @Override
    public void configure() throws Exception {

        log.info("Route started !!");
        DataFormat dataFormat = new BindyCsvDataFormat(Item.class);

        from("{{fromRoute}}")
                .log("timer invoked : " + env.getProperty("message"))

                .to("{{toRoute}}")
                .unmarshal(dataFormat)
                .log("Unmarshalled Objects are : ${body}");

        log.info("Route Ended !!");

    }
}
