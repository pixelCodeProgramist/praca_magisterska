package com.example.userservice.userMenager.api.controller;

import com.example.userservice.userMenager.api.request.EmployeeRequest;
import com.example.userservice.userMenager.api.response.ResponseView;
import com.example.userservice.userMenager.business.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/user/employee")
@AllArgsConstructor
@Validated
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseView register(@Valid @RequestBody EmployeeRequest employeeRequest) {
        employeeService.addEmployee(employeeRequest);
        return new ResponseView("Pracownik zarejestrowany poprawnie");
    }

}
