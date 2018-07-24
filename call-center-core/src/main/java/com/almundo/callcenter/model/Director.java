package com.almundo.callcenter.model;

/**
 * Represents a director, who is an employee with priority 3.
 */
public class Director extends Employee{
    public Director(String name, Boolean inACall) {
        super(3, name, inACall);
    }
}
