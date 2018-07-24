package com.almundo.callcenter.model;

import com.almundo.callcenter.exception.InvalidPriorityException;

/**
 * Factory of the {@link Employee}
 */
public class EmployeeFactory {
    /**
     * Builds a new instance od an employee based on the priority, if the priority is invalid an {@link InvalidPriorityException}
     * will be thrown.
     * @param id The id of the employee.
     * @param priority The priority to answer a call.
     * @param inACall Indicates if the client is in a call.
     * @return A new employee and if the priority is invalid an {@link InvalidPriorityException} will be thrown.
     * @throws InvalidPriorityException when the priority is invalid.
     */
    public static Employee getNewEmployee(String id, Integer priority, Boolean inACall) throws InvalidPriorityException {
        if (1 == priority) {
            return  new Operator(id, inACall);
        } else if ( 2  == priority) {
            return  new Supervisor(id, inACall);
        } else if (3 == priority ) {
            return new Director(id, inACall);
        } else {
            throw new InvalidPriorityException("The Priority " + priority + "is invalid");
        }
    }
}
