package com.almundo.callcenter.repository;

import com.almundo.callcenter.model.Employee;

import java.util.List;

/**
 * Interface for a employee repository.
 */
public interface IEmployeeRepository {
    /**
     * Find all the employees for the system or an empty list.
     * @return a list of all the employees or an empty list.
     */
    public List<Employee> findAllEmployees();

    /**
     * Update the information of the employee.
     * @param employee The employe to be updated.
     */
    public void updateEmployee(Employee employee);
}
