package com.sam.rest.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestOperationParamDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class RestletRoute extends RouteBuilder {

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Override
    public void configure() throws Exception {

        onException(Exception.class).handled(true)
                .transform(exceptionMessage())
                .log(LoggingLevel.ERROR, "Exception in the route ${exceptionMessage}")
                ;

        restConfiguration()
                .component("restlet")
                .apiContextRouteId("service1")
                .bindingMode(RestBindingMode.auto)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Example of REST API").apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .host("localhost").port("8888")
        ;

        rest("/employee")
                .post().to("direct:postEmployees").description("Add New Employee")
                .get().to("direct:getEmployees").description("All Employees list")
                .get("/{empId}").to("direct:getEmployeeId").description("Find Employee by Id")
                .put("/{empId}").to("direct:putEmpId").description("Modify Emp by ID")
                .delete("/{empId}").to("direct:deleteEmployeeId").description("Delete emp by Id")
        ;

        from("direct:getEmployees")
                .setBody(simple("select * from employee"))
                .to("jdbc:dataSource")
        ;

        from("direct:getEmployeeId")
                .setBody(simple("select * from employee where id = ${header.empId}"))
                .to("jdbc:dataSource")
        ;

        from("direct:postEmployees")
                .setBody(simple("INSERT INTO employee ( department, employee_name, employee_salary) VALUES "+
                        "('${body[department]}', '${body[employee_name]}', ${body[salary]})"))
                .to("jdbc:dataSource")
        ;

        from("direct:putEmpId")
                .setBody(simple("update employee set department='${body[department]}', employee_name='${body[employee_name]}'," +
                        "employee_salary = ${body[salary]} where id = ${header.empId}"))
                .to("jdbc:dataSource")
        ;

        from("direct:deleteEmployeeId")
                .setBody(simple("delete from employee where id = ${header.empId}"))
                .to("jdbc:dataSource")
        ;
    }
}

