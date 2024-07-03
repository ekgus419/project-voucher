package com.web.projectvoucher.app.domain.employee;

import com.web.projectvoucher.app.controller.response.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private final Map<Long, EmployeeResponse> employeeResponseMap = new HashMap<>();

    public Long create(final String name, final String position, final String department) {
        Long no = employeeResponseMap.size() + 1L;
        employeeResponseMap.put(no, new EmployeeResponse(no, name, position, department));
        return no;
    }

    public EmployeeResponse get(final Long no) {
        return employeeResponseMap.get(no);
    }

}
