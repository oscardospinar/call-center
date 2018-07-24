package com.almundo.callcenter.model;

/**
 * Represents a operator, who is an employee with priority 1.
 */
public class Operator extends Employee {

    public Operator(String name, boolean inACall) {
        super(1, name, inACall);
    }
}
