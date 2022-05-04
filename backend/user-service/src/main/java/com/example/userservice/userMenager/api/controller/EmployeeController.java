package com.example.userservice.userMenager.api.controller;

import com.example.userservice.userMenager.api.request.EmployeeRequest;
import com.example.userservice.userMenager.api.request.EmployeeUpdateRequest;
import com.example.userservice.userMenager.api.response.EmployeeUserView;
import com.example.userservice.userMenager.api.response.ResponseView;
import com.example.userservice.userMenager.business.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/all")
    public List<EmployeeUserView> getAllEmployees(HttpServletRequest httpServletRequest) {
        return employeeService.getAllEmployees(httpServletRequest);
    }

    @PatchMapping("/remove/{id}")
    public ResponseView removeEmployeeAfterId(@PathVariable("id") Long id) {
        employeeService.changeEmployeeStatusOnInactive(id);
        return new ResponseView("Pracownik usunięty poprawnie");
    }

    @PutMapping("/update")
    public ResponseView updateEmployeeAfterId(@Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        employeeService.updateEmployee(employeeUpdateRequest);
        return new ResponseView("Pracownik zaktualizowany poprawnie");
    }





}
