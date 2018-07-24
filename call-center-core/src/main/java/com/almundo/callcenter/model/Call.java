package com.almundo.callcenter.model;

import com.almundo.callcenter.core.CallProcessor;
import com.almundo.callcenter.repository.IEmployeeRepository;
import lombok.Data;

import java.util.*;

/**
 * Represents a call in the system.
 */
@Data
public class Call {

    /**
     * The duration of the call.
     */
    private final int duration;

    private boolean processed;

    private final String id;

    /**
     * Constructor with the duration of the call and the processed field in false.
     * @param duration The duration of the call.
     */
    public Call (int duration, String id) {
        this.duration = duration;
        this.id = id;
        this.processed = false;
    }

    /**
     * Creates a timer task with the duration of the call an when it finish the call is ended.
     * @param employee The employee who answer the call.
     * @param employeeRepository The repository to notify the changes.
     */
    public void answer(final Employee employee, IEmployeeRepository employeeRepository) {
        TimerTask task = new TimerTask() {
            public void run() {
                processed = true;
                System.out.println("Ending call: " + id);
                CallProcessor.endCall(employee, employeeRepository);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, duration);
    }
}
