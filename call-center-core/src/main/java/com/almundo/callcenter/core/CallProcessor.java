package com.almundo.callcenter.core;

import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.repository.IEmployeeRepository;
import lombok.Data;

/**
 * Process the call when this ended
 */
@Data
public class CallProcessor {

    /**
     * Ends a call.
     * @param employee The employee attending the call.
     * @param repository The repository to update the status.
     */
    public static void endCall(Employee employee, IEmployeeRepository repository) {
        employee.endCall();
        repository.updateEmployee(employee);
    }
}
