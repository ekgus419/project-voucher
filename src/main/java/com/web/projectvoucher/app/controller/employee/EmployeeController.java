package com.web.projectvoucher.app.controller.employee;

import com.web.projectvoucher.app.controller.employee.request.EmployeeCreateRequest;
import com.web.projectvoucher.app.controller.employee.response.EmployeeResponse;
import com.web.projectvoucher.domain.employee.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 사원 생성
    @PostMapping("/api/v1/employee")
    public Long create(@RequestBody final EmployeeCreateRequest request) {
        return employeeService.create(request.name(), request.position(), request.department());
    }

    // 사원 조회
    @GetMapping("/api/v1/employee/{no}")
    public EmployeeResponse get(@PathVariable(value = "no") final long no) {
        return employeeService.get(no);
    }
}
