package com.almundo.callcenter.model;

import com.almundo.callcenter.repository.IEmployeeRepository;
import lombok.Data;

/**
 * Represents an employee which can answer a {@link Call}.
 */
@Data
public abstract class Employee {
    /**
     * The priority to answer the call.
     */
    private final int priority;
    /**
     * indicates if the employee is in a call.
     */
    private boolean inACall;
    /**
     * The id of the employee.
     */
    private final String id;

    /**
     * Constructor of the superclass.
     * @param priority The priority to answer the call.
     * @param id The id of the employee.
     * @param inACall Indicates if the employee is in a call.
     */
    Employee(int priority, String id, boolean inACall) {
        this.priority = priority;
        this.id = id;
        this.inACall = inACall;
    }

    /**
     * Answer a call.
     * @param call the call to answer.
     * @param employeeRepository The repository to notify the changes.
     */
    public void answerCall(Call call, IEmployeeRepository employeeRepository) {
        this.inACall = true;
        call.answer(this, employeeRepository);
    }

    /**
     * Ends a call.
     */
    public void endCall() {
        this.inACall = false;
    }
}
