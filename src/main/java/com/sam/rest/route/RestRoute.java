package com.sam.rest.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class RestRoute extends RouteBuilder {

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Override
    public void configure() throws Exception {

        onException(Exception.class).handled(true)
                .transform(exceptionMessage())
                .log(LoggingLevel.ERROR, "Exception in the route ${exception.message}")
                ;

        restConfiguration()
                .component("restlet")
                .apiContextRouteId("service2")
                .bindingMode(RestBindingMode.auto)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Example of REST API").apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .host("localhost").port("9999")
        ;

        rest("/customer")
                .post().to("direct:postCustomer")
                .get().to("direct:getCustomer").description("All Customers list")
//                .get("/{empId}").to("direct:getEmployeeId").description("Find Employee by Id")
//                .put("/{empId}").to("direct:putEmpId").description("Modify Emp by ID")
//                .delete("/{empId}").to("direct:deleteEmployeeId").description("Delete emp by Id")
        ;

        from("direct:getCustomer")
                .setBody(simple("select * from employee"))
                .to("jdbc:dataSource")
        ;

       /* from("direct:getEmployeeId")
                .setBody(simple("select * from employee where id = ${header.empId}"))
                .to("jdbc:dataSource")
       */ ;

        from("direct:postCustomer")
                .setBody(simple("INSERT INTO customer (cust_name, cust_address, product, quantity) VALUES "+
                        "('${body[cust_name]}', '${body[cust_address]}', '${body[product]}', ${body[quantity]})"))
                .to("jdbc:dataSource")
        ;

       /* from("direct:putEmpId")
                .setBody(simple("update employee set department='${body[department]}', employee_name='${body[employee_name]}'," +
                        "employee_salary = ${body[salary]} where id = ${header.empId}"))
                .to("jdbc:dataSource")
        ;

        from("direct:deleteEmployeeId")
                .setBody(simple("delete from employee where id = ${header.empId}"))
                .to("jdbc:dataSource")
       */ ;
    }
}

