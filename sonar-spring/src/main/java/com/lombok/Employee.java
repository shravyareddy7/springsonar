package com.lombok;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

//@Getter
//@Setter
//@NoArgsConstructor(force = true)
//@ToString
//@EqualsAndHashCode
//@AllArgsConstructor
//@RequiredArgsConstructor
@Data
@Accessors(chain = true)
@Builder
@Slf4j
public class Employee {

    @NonNull
    private int id;
    private String name;
    private double salary;
    private final String company;
    public void simpleDebugExample() {
        log.debug("This is a DEBUG level log. Current employee ID is: {}", id);
    }

    public void checkSalary(double newSalary) {
        if (newSalary <= 0) {
            log.warn("WARN: The provided salary is negative: {}", newSalary);
        } else {
            log.info("INFO: The provided salary is valid: {}", newSalary);
        }
    }

    public void traceExample() {
        log.trace("TRACE: Entering the traceExample method.");
        log.trace("TRACE: Exiting the traceExample method.");
    }

    public void errorExample() {
        try {
            log.info("INFO: About to perform a risky operation.");

            int result = 10 / 0;
            log.info("INFO: The result is: {}", result);
        } catch (Exception e) {
            log.error("ERROR: An exception occurred: {}", e.getMessage());
        }
    }

    public void printDetails() {
        log.info("Employee Details:");
        log.info("ID: {}", id);
        log.info("Name: {}", name);
        log.info("Salary: {}", salary);
        log.info("Company: {}", company);
    }
}
