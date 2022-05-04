package com.example.userservice.userMenager.api.controller;

import com.example.userservice.userMenager.api.request.EmployeeUpdateRequest;
import com.example.userservice.userMenager.api.response.DetailUserView;
import com.example.userservice.userMenager.api.response.ResponseView;
import com.example.userservice.userMenager.business.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/user/client")
@AllArgsConstructor
@Validated
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/active/all")
    public List<DetailUserView> getAllActiveClients() {
        return employeeService.getAllActiveClients();
    }

    @GetMapping("/all")
    public List<DetailUserView> getAllClients() {
        return employeeService.getAllClients();
    }

    @PatchMapping("/remove/{id}")
    public ResponseView removeClient(@PathVariable("id") Long id) {
        employeeService.changeClientStatusOnInactive(id);
        return new ResponseView("Klient usunięty poprawnie");
    }

    @GetMapping("/activate/{id}")
    public ResponseView activateClient(@PathVariable("id") Long id) {
        employeeService.activateClient(id);
        return new ResponseView("Klient zaktywowany!");
    }

    @PutMapping("/update")
    public ResponseView updateClient(@Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        employeeService.updateClient(employeeUpdateRequest);
        return new ResponseView("Klient zaktualizowany poprawnie");
    }



}
