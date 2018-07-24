package com.almundo.callcenter.schedule;

import com.almundo.callcenter.exception.CallCenterException;
import com.almundo.callcenter.exception.EmployeeUnavailableException;
import com.almundo.callcenter.exception.NoEmployeesExist;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.repository.IEmployeeRepository;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Task which dispatch the call of the queue to the employees.
 */
@Data
public class AnswerCallsTask implements Runnable {

    /**
     * The repository to synchronize the employees data.
     */
    private IEmployeeRepository employeeRepository;

    /**
     * The queue of calls.
     */
    private final ConcurrentLinkedQueue<Call> calls;

    /**
     * Constructor of the task.
     *
     * @param calls              The queue of the calls.
     * @param employeeRepository The repository employee.
     */
    public AnswerCallsTask(ConcurrentLinkedQueue<Call> calls, IEmployeeRepository employeeRepository) {
        this.calls = calls;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Dispatch the calls from the queue to employee while the queue contains calls if there is no employees available
     * it will add the call to the queue until a employee is available.
     */
    @Override
    public void run() {
        Call call;
        while ((call = calls.poll()) != null) {
            try {
                Employee employee = getAvailableEmployee();
                employee.answerCall(call, employeeRepository);
                saveEmployee(employee);
            } catch (NoEmployeesExist e) {
                e.printStackTrace();
                throw e;
            } catch (EmployeeUnavailableException e) {
                calls.add(call);
            } catch (CallCenterException e) {
                e.printStackTrace();
                calls.add(call);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save an employee in the repository.
     * @param employee the employee to save.
     */
    private void saveEmployee(Employee employee) {
        employeeRepository.updateEmployee(employee);
    }

    /**
     * Find all the employees of the system.
     * @return A list od the employees or a empty list.
     */
    private List<Employee> findAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    /**
     * Retrieves an employee who is not in a call and has the lower priority of all.
     * @return The employee with the lowest priority and available.
     * @throws NoEmployeesExist when there is no employees in the system
     * @throws EmployeeUnavailableException when there is no employees available.
     */
    private Employee getAvailableEmployee() throws NoEmployeesExist, EmployeeUnavailableException{
        List<Employee> allEmployees = findAllEmployees();
        if (allEmployees.isEmpty()) {
            throw new NoEmployeesExist("");
        }
        List<Employee> employees = Collections.synchronizedList(allEmployees);
        List<Employee> employeesOrdered = employees.stream()
                .filter(em -> !em.isInACall())
                .sorted(Comparator.comparing(Employee::getPriority))
                .collect(Collectors.toList());
        if (!employeesOrdered.isEmpty()) {
            return employeesOrdered.get(0);
        } else {
            throw new EmployeeUnavailableException("");
        }
    }
}
