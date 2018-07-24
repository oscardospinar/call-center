package com.almundo.callcenter.core;

import com.almundo.callcenter.model.*;
import com.almundo.callcenter.repository.IEmployeeRepository;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Test for the class {@link Dispatcher}
 */
public class DispatcherTest {

    /**
     * Queue used by the dispatcher.
     */
    private ConcurrentLinkedQueue<Call> calls = new ConcurrentLinkedQueue<>();
    /**
     * A count down use to verify all the calls of the answer method from the call.
     */
    private CountDownLatch latch = new CountDownLatch(10);

    /**
     * The dispatcher to test, there id only one instance.
     */
    private Dispatcher dispatcher = Dispatcher.getInstance(new IEmployeeRepository() {

        @Override
        public List<Employee> findAllEmployees() {
            List<Employee> employees = new ArrayList<>();
            employees.add(new Director("Director", false));
            employees.add(new Supervisor("Supervisor", false));
            employees.add(new Operator("Operator", false));
            return employees;
        }

        @Override
        public void updateEmployee(Employee employee) {

        }
    });

    /**
     * Verify that the method can be called concurrently by at least 10 threads, this was achieved using a {@link ConcurrentLinkedQueue}
     * to enqueue the calls and a task in other thread dispatch the calls.
     */
    @Test(threadPoolSize = 10, invocationCount = 10)
    public void dispatchCallOk() {
        Random randomId = new Random();
        Random randomDuration = new Random();
        Call call = new Call(randomDuration.nextInt(10) * 1000, String.valueOf(randomId.nextInt(10))) {
            @Override
            public void answer(Employee employee, IEmployeeRepository employeeRepository) {
                this.setProcessed(true);
                employee.endCall();
                latch.countDown();
            }
        };
        calls.add(call);
        dispatcher.dispatchCall(call);
    }

    /**
     * Verify that all the calls answered by the employees.
     * @throws InterruptedException when the awaits fails.
     */
    @AfterTest
    private void verifyCalls() throws InterruptedException {
        Call call;
        if(!latch.await(20, TimeUnit.SECONDS)){
            fail("Timed out, see log for errors");
        }
        while ((call = calls.poll()) != null) {
            assertTrue(call.isProcessed());
        }
    }
}