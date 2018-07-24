package com.almundo.callcenter.model;

/**
 * Represents a supervisor, who is an employee with priority 2.
 */
public class Supervisor extends Employee {
    public Supervisor(String name, Boolean inACall) {
        super(2, name, inACall);
    }
}
