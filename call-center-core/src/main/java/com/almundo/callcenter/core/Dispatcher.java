package com.almundo.callcenter.core;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.repository.IEmployeeRepository;
import com.almundo.callcenter.schedule.AnswerCallsTask;
import lombok.Data;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The class in charge of assign the calls to the agents.
 */
@Data
public class Dispatcher {

    /**
     * The unique instance of the class.
     */
    private static Dispatcher dispatcher = null;
    /**
     * The queue of the calls to be processed.
     */
    private ConcurrentLinkedQueue<Call> calls = new ConcurrentLinkedQueue<>();
    /**
     * The task used to assign the calls from the queue.
     */
    private final Thread answerCallsTask;

    /**
     * Constructor of the class which schedule the task {@link AnswerCallsTask}.
     * @param employeeRepository The repository used to save of find the state of the agents.
     */
    private Dispatcher(IEmployeeRepository employeeRepository) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
        answerCallsTask = new Thread(new AnswerCallsTask(calls, employeeRepository));
        scheduler.schedule(answerCallsTask, 1L, TimeUnit.SECONDS);
    }

    /**
     * Get a new instance of this class if already exist the method return the known instance.
     * @param employeeRepository The repository for the new instance.
     * @return The new instance of this class if already exist the method return the known instance.
     */
    public static Dispatcher getInstance(IEmployeeRepository employeeRepository) {
        if (dispatcher == null) {
            dispatcher = new Dispatcher(employeeRepository);
        }
        return dispatcher;
    }

    /**
     * Class use to dispatch the calls, the calls are enqueue in order to be proccessed from the queue.
     * @param call the call to be processed.
     */
    public void dispatchCall(Call call) {
        calls.add(call);
    }
}
