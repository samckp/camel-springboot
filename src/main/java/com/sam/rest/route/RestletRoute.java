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
                .host("localhost").port("8888");

        rest("/employee")
                .post().to("direct:postEmployees")
                .get().to("direct:getEmployees")
                .get("/{empId}").to("direct:getEmployeeId")
                .put("/{personId}").to("direct:putEmpId")
                .delete("/{empId}").to("direct:deleteEmployeeId");

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

