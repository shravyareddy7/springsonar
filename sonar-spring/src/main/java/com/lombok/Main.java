package com.lombok;

public class Main {
    public static void main(String[] args) {
        Employee employee = Employee.builder()
                .id(1)
                .name("Ram")
                .salary(50000)
                .company("Zemoso")
                .build();

        employee.printDetails();

        employee.simpleDebugExample();
        employee.checkSalary(-500);
        employee.checkSalary(60000);
        employee.traceExample();
        employee.errorExample();
    }
}
