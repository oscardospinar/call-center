package com.almundo.callcenter.repository;

import com.almundo.callcenter.generated.db.tables.records.EmployeeRecord;
import com.almundo.callcenter.model.*;
import lombok.Data;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.almundo.callcenter.generated.db.tables.Employee.EMPLOYEE;

/**
 * Implementation of the repository of the class employee, use jooq to execute the queries in the database..
 */
@Data
@Repository
public class EmployeeRepository implements IEmployeeRepository {

    /**
     * Dsl used to executes the queries in the database.
     */
    @Autowired
    private DSLContext dsl;

    /**
     * Retrieves all the employees from the database.
     * @return A list with all the employees or an empty list.
     */
    public List<Employee> findAllEmployees() {
        List<EmployeeRecord> records = dsl.selectFrom(EMPLOYEE).fetch();
        List<Employee> employees =records.stream()
                .map(record -> EmployeeFactory.getNewEmployee(record.component1(), record.component2(), record.component3()))
                .collect(Collectors.toList());
        return employees == null || employees.isEmpty() ? Collections.EMPTY_LIST : employees;
    }

    /**
     * Update an employee.
     * @param employee The employee to be updated.
     */
    public void updateEmployee(Employee employee) {
        dsl.update(EMPLOYEE).set(EMPLOYEE.IN_A_CALL, employee.isInACall()).where(EMPLOYEE.ID.eq(employee.getId())).execute();
    }
}
