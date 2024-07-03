package com.web.projectvoucher.app.domain.employee;

import com.web.projectvoucher.app.controller.response.EmployeeResponse;
import com.web.projectvoucher.storage.employee.EmployeeEntity;
import com.web.projectvoucher.storage.employee.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Long create(final String name, final String position, final String department) {
        final EmployeeEntity employeeEntity = employeeRepository.save(new EmployeeEntity(name, position, department));
        return employeeEntity.id();
    }

    public EmployeeResponse get(final Long no) {
        final EmployeeEntity employeeEntity = employeeRepository.findById(no)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return new EmployeeResponse(employeeEntity.id(), employeeEntity.name(), employeeEntity.position(), employeeEntity.department());
    }

}
