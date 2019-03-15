package com.sam.rest.route;

import org.apache.camel.builder.RouteBuilder;
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

        onException(Exception.class).handled(true).log("Exception caught !!");

        restConfiguration()
                .component("restlet")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Example API").apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .host("localhost").port("8888");

        rest("/swagger")
                .produces("text/html")
                .get("/index.html")
                .responseMessage().code(200).message("Swagger UI").endResponseMessage()
                .to("direct://get/swagger/ui/path");

        from("direct://get/swagger/ui/path")
                .routeId("SwaggerUI")
                .setBody().simple("resource:classpath:/swagger/index.html");


        rest("/employee")
                .post().to("direct:postEmployees")
                .get().to("direct:getEmployees").description("All Employees list")
                .get("/{empId}").to("direct:getEmployeeId").description("Find Employee by Id")
                .put("/{empId}").to("direct:putEmpId").description("Modify Emp by ID")
                .delete("/{empId}").to("direct:deleteEmployeeId").description("Delete emp by Id");

        from("direct:getEmployees")
                .setBody(simple("select * from employee"))
                .log("${body}")
                .to("jdbc:dataSource");

        from("direct:getEmployeeId")
                .setBody(simple("select * from employee where id = ${header.empId}"))
                .to("jdbc:dataSource");

        from("direct:postEmployees")
                .setBody(simple("insert into employee(department, employee_name, employee_salary) " +
                        "values('${header.department}','${header.employee_name}'),${header.employee_salary})"))
                .to("jdbc:dataSource")
                .setBody(simple("select * from employee where id in (select max(id) from employee)"))
                .to("jdbc:dataSource");

        from("direct:putEmpId")
                .setBody(simple("update employee set department='${header.department}', employee_name='${header.employee_name}'," +
                        "employee_salary=${header.employee_salary} where id = ${header.personId}"))
                .to("jdbc:dataSource");

        from("direct:deleteEmployeeId")
                .setBody(simple("delete from employee where id = ${header.empId}"))
                .to("jdbc:dataSource");


    }

    }

