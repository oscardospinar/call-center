package com.almundo.callcenter.schedule;


import com.almundo.callcenter.exception.NoEmployeesExist;
import com.almundo.callcenter.model.*;
import com.almundo.callcenter.repository.IEmployeeRepository;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Test class for the {@link AnswerCallsTask}
 */
public class AnswerCallsTaskTest {

    /**
     * Instance to be tested.
     */
    private AnswerCallsTask task;
    /**
     * Queue used by the task.
     */
    private ConcurrentLinkedQueue<Call> calls = new ConcurrentLinkedQueue<>();


    /**
     * Initialize with a task with a default repository.
     */
    @BeforeClass
    public void init() {
        task = new AnswerCallsTask(calls, new IEmployeeRepository() {

            @Override
            public List<Employee> findAllEmployees() {
                return Collections.EMPTY_LIST;
            }

            @Override
            public void updateEmployee(Employee employee) {

            }
        });
    }

    /**
     * Verify that a Operator answer the call.
     */
    @Test
    public void testRunOk() {
        Call call = buildRandomCall();
        calls.add(call);
        task.setEmployeeRepository(new IEmployeeRepository() {

            List<Employee> employees = new ArrayList<>();

            @Override
            public List<Employee> findAllEmployees() {
                employees.add(new Director("Director", false));
                employees.add(new Supervisor("Supervisor", false));
                employees.add(new Operator("Operator", false) {
                    @Override
                    public void answerCall(Call call, IEmployeeRepository employeeRepository) {
                        this.setInACall(true);
                    }
                });
                return employees;
            }

            @Override
            public void updateEmployee(Employee employee) {

            }
        });
        Employee employee = task.getEmployeeRepository().findAllEmployees().get(2);
        task.run();
        Assert.assertEquals(employee.getPriority(), 1);
        Assert.assertTrue(employee.isInACall());
    }

    /**
     * Verify that a {@link NoEmployeesExist} is thrown by the task when there is no employees in the repository.
     */
    @Test(expectedExceptions = NoEmployeesExist.class)
    public void testAllEmployeesBusy() {
        Call call = buildRandomCall();
        calls.add(call);
        task.setEmployeeRepository(new IEmployeeRepository() {
            @Override
            public List<Employee> findAllEmployees() {
                return Collections.EMPTY_LIST;
            }

            @Override
            public void updateEmployee(Employee employee) {

            }
        });
        task.run();
    }

    /**
     * Build a {@link Call} with random data.
     * @return a random {@link Call}
     */
    private Call buildRandomCall() {
        Random randomId = new Random();
        Random randomDuration = new Random();
        return new Call(randomDuration.nextInt(10) * 1000, String.valueOf(randomId.nextInt(10))) {
            @Override
            public void answer(Employee employee, IEmployeeRepository employeeRepository) {
                this.setProcessed(true);
                employee.endCall();
            }
        };
    }
}